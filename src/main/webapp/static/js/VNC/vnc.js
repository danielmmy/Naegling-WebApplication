/**
 * 
 */
//vnc Object constructor
Vnc=function(url,canvasId){
	//canvas to draw framebuffers
	this.canvas=document.getElementById(canvasId);
	//canvas 2d context
	this.context=this.canvas.getContext("2d");
	//Websocket 
	this.ws=new WebSocket(url);
	this.ws.binaryType = "arraybuffer";
	//Entry buffer
	this.inBuffer=new Buffer();
	this.buttonMask=0;
	bindWebSocketHandlers(this);
	this.handler=rfbVersionHandler;
	
}


var str2ab=function(str) {
	  var buf = new ArrayBuffer(str.length*1); // 2 bytes for each char
	  var bufView = new Uint8Array(buf);
	  for (var i=0, strLen=str.length; i<strLen; i++) {
	    bufView[i] = str.charCodeAt(i);
	  }
	  return buf;
};


//Bind WebSocket callback methods 
var bindWebSocketHandlers = function(vnc) {
    vnc.ws.onopen = function(event) {
    	console.log("connected.");
    	var str = document.getElementById("nodeId").getAttribute("value");
		vnc.ws.send(str2ab(str));
    }

    var buff = vnc.inBuffer;
    vnc.ws.onmessage = function(event){
        // Append bytes to input buffer.
        buff.append(event.data);
        // Read handler loop.
        while(vnc.handler(vnc)) {
            // Do nothing.
        }
    }

    vnc.ws.onclose = vnc.ws.onerror = function() {
        console.log("Connection closed");
    }
};

//Send messages to WebSocket server
var sendToWebSocket=function(vnc,message){
	vnc.ws.send(message);
};

//Handle RFB protocol version handshake
var rfbVersionHandler = function(vnc) {
    if (vnc.inBuffer.length < 12) {
        return false;
    }

    var version = new Uint8Array(vnc.inBuffer.read(12));
    console.log("Version: " + String.fromCharCode.apply(null, version));
    // Echo back version.
    sendToWebSocket(vnc, version.buffer)

    // Set next handler.
    vnc.handler = securityTypesQuantityHandler;
    return true;
};

var securityTypesQuantityHandler = function(vnc) {
    if (vnc.inBuffer.length < 1) {
        return false;
    }
    
    //Set the number of security types
    vnc.numSecurityTypes = vnc.inBuffer.readUint8();
    console.log("Number of security types: " + vnc.numSecurityTypes);

    // Set next handler.
    vnc.handler = securityTypesHandler;
    return true;
};


var securityTypesHandler = function(vnc) {
    var numSecurityTypes = vnc.numSecurityTypes;
    if (vnc.inBuffer.length < numSecurityTypes) {
        return false;
    }

    //Security types options
    var securityTypes = [];
    for (var i=0; i< numSecurityTypes; i++) {
        securityTypes.push(vnc.inBuffer.readUint8());
    }

    //Select none security type(1)
    var noneType = new Uint8Array([1]);
    sendToWebSocket(vnc, noneType.buffer);
    console.log("Selected none security type.")

    // Set next handler.
    vnc.handler = authenticationHandler;
    return true;
};


var authenticationHandler = function(vnc) {
    if (vnc.inBuffer.length < 4) {
        return false;
    }
    var authSuccess = vnc.inBuffer.read(4);
    var word = new Uint32Array(authSuccess);
    if (word[0] === 1) {
        throw new Error("Auth failed");
    }else if(word[0]===0){
    	console.log("Authentication success.");
    	// Set next handler.
    	vnc.handler = desktopSharingHandler;
    	return true;
    }

    return false;
   
};

var desktopSharingHandler=function(vnc){
	//Do not allow desktop sharing 
	var allowSharing = new Uint8Array([0]);
    sendToWebSocket(vnc,allowSharing.buffer);
    // Set next handler.
	vnc.handler = initializationMessagesHandler;
	return true;
};


var initializationMessagesHandler = function(vnc) {
    if (vnc.inBuffer.length < 24) {
        return false;
    }

    // Get dimensions.
    var width = vnc.inBuffer.readUint16();
    console.log("Width: " + width);
    var height = vnc.inBuffer.readUint16();
    console.log("Height: " + height);
    vnc.width = width;
    vnc.height = height;
    vnc.canvas.setAttribute("width",vnc.width);
    vnc.canvas.setAttribute("height",vnc.height);
    //Center canvas
    var wWidth=window.innerWidth;
    var wHeight=window.innerHeight;
    wWidth-=vnc.width;
    wHeight-=vnc.height;
    vnc.canvas.style.marginLeft= wWidth/2;
    vnc.canvas.style.marginTop=wHeight/2;
    //Bind events
    bindHandlers(vnc);

    // Get pixel format. Ignore it and assume hardcoded default format.
    var bitsPerPixel = vnc.inBuffer.readUint8();
    var depth = vnc.inBuffer.readUint8();
    var bigEndian = vnc.inBuffer.readUint8();
    var trueColor = vnc.inBuffer.readUint8();
    var redMax = vnc.inBuffer.readUint16();
    var greenMax = vnc.inBuffer.readUint16();
    var blueMax = vnc.inBuffer.readUint16();
    var redShift = vnc.inBuffer.readUint8();
    var greenShift = vnc.inBuffer.readUint8();
    var blueShift = vnc.inBuffer.readUint8();
    vnc.inBuffer.read(3);     // 3 bytes of padding

    // Get name length.
    vnc.nameLength = vnc.inBuffer.readUint32();

    // Set next handler.
    vnc.handler = nameHandler;
    return true;
};

var nameHandler = function(vnc) {
    var nameLength = vnc.nameLength;

    if (vnc.inBuffer.length < nameLength) {
        return false;
    }

    var nameB = new Uint8Array(vnc.inBuffer.read(nameLength));
    vnc.name = String.fromCharCode.apply(null, nameB);
    console.log("Connected to: " + vnc.name);

    // Set allowed encodings.
    setEncodings(vnc);
    
    //Start requesting frames.
    updateRequest(vnc, 0);


    // Set next handler.
    vnc.handler = defaultHandler;
    return true;
};

//FIXME
var defaultHandler = function(vnc) {
    if (vnc.inBuffer.length < 4) {
        return false;
    }
    var type = vnc.inBuffer.readUint8();

    switch(type) {
        case 0:
            vnc.inBuffer.read(1) // padding
            vnc.numRectangles = vnc.inBuffer.readUint16();
            vnc.handler = rectangleHeaderHandler;
            break;
        default:
            throw new Error("Unknown message type: " + type);
    }
    return true;
};

var rectangleHeaderHandler = function(vnc) {
    if (vnc.inBuffer.length < 12) {
        return false;
    }

    var header = {};

    header.xPos            = vnc.inBuffer.readUint16();
    header.yPos            = vnc.inBuffer.readUint16();
    header.width           = vnc.inBuffer.readUint16();
    header.height          = vnc.inBuffer.readUint16();
    header.encodingType    = vnc.inBuffer.readUint32();
    

    // Set next handler.
    switch(header.encodingType) {
        case 0:
            vnc.handler = rawHandler;
            break;
        case 1:
            vnc.handler = copyRectHandler;
            break;
        default:
            throw new Error("Unknown encoding type: " + header.encodingType);
    }

    vnc.header = header;
    return true;
};
var rawHandler = function rawHandler(vnc) {
    var header = vnc.header;
    // Assume 32 bits per pixel hardcoded.
    var length = header.width * header.height * (4);

    if (vnc.inBuffer.length < length) {
        return false;
    }

    var pixels = new Uint8Array(vnc.inBuffer.read(length));

    // Paint on screen.
    putPixels(vnc,pixels,header.width, header.height, header.xPos, header.yPos);

    // Set next handler.
    vnc.numRectangles--;
    if (vnc.numRectangles) {
        vnc.handler = rectangleHeaderHandler;
    } else {
        vnc.handler = defaultHandler;
        updateRequest(vnc, 1);
    }
    return true;
};

var putPixels=function(vnc,array,width,height, xPos, yPos) {
    var imageData = vnc.context.createImageData(width, height);
    copyAndTransformImageData(array, imageData);
    vnc.context.putImageData(imageData, xPos, yPos);
};

var copyRect = function (vnc,width, height, xPos, yPos, xSrc, ySrc){
    var imageData = vnc.context.getImageData(xSrc, ySrc, width, height);
    vnc.context.putImageData(imageData, xPos, yPos);
};

function copyAndTransformImageData(array, imageData) {
    var data = imageData.data;

    for (var i=0; i<array.length; i+=4) {
        data[i] = array[i+2];       // red
        data[i+1] = array[i+1];     // green
        data[i+2] = array[i];       // blue
        data[i+3] = 255;            // alpha
    }
};

var copyRectHandler = function(vnc) {
    if (vnc.inBuffer.length < 4) {
        return false;
    }

    var header = vnc.header;
    var xSrc = vnc.inBuffer.readUint16();
    var ySrc = vnc.inBuffer.readUint16();

    // Copy rectangle on screen.
    copyRect(vnc,header.width, header.height, header.xPos, header.yPos, xSrc, ySrc);

    // Set next handler.
    vnc.numRectangles--;
    if (vnc.numRectangles) {
        vnc.handler = rectangleHeaderHandler;
    } else {
        vnc.handler = defaultHandler;
        updateRequest(vnc, 1);
    }
    return true;
};

var setEncodings = function(vnc){
  var outBuffer = new Buffer();

  outBuffer.appendBytes(2, 0)       // type(u8 2) padding (u8 0)
  outBuffer.appendUint16(2);        // num encodings (u16 2)
  outBuffer.appendUint32(0);        // raw (s32 0)
  outBuffer.appendUint32(1);        // copyRect (s32 1)

  sendToWebSocket(vnc, outBuffer.read(outBuffer.length));
};

var updateRequest = function(vnc, incremental){
    var outBuffer = new Buffer();

    outBuffer.appendBytes(3);             // type (u8 3)
    outBuffer.appendBytes(incremental);   // incremental

    outBuffer.appendBytes(0,0,0,0);       // top left corner: x (u16 0) y (u16 0)
    outBuffer.appendUint16(vnc.width);  
    outBuffer.appendUint16(vnc.height); 

    sendToWebSocket(vnc,outBuffer.read(outBuffer.length));
};

Vnc.prototype.mouseDownHandler = function(vnc, event) {
    if (event.which == 1) {
        // left click
        vnc.buttonMask ^= 1;
    } else if (event.which == 3) {
        // right click
        vnc.buttonMask ^= (1<<2);
    }
    sendMouseEvent(vnc, event);
}

Vnc.prototype.mouseUpHandler = function(vnc, event) {
    if (event.which == 1) {
        // left click
        vnc.buttonMask ^= 1;
    } else if (event.which == 3) {
        // right click
        vnc.buttonMask ^= (1<<2);
    }
    sendMouseEvent(vnc, event);
};

// mousewheel up and down is represented by a press and release of buttons
// 4 and 5 respectively.
Vnc.prototype.mouseWheelHandler = function(vnc, event) {
    if(event.wheelDelta > 0) {
        vnc.buttonMask |= (1<<3);
        sendMouseEvent(vnc, event);
        vnc.buttonMask ^= (1<<3);
        sendMouseEvent(vnc, event);
    } else {
        vnc.buttonMask |= (1<<4);
        sendMouseEvent(vnc, event);
        vnc.buttonMask ^= (1<<4);
        sendMouseEvent(vnc, event);
    }
};


Vnc.prototype.mouseMoveHandler = function(vnc, event) {
    sendMouseEvent(vnc, event);
};

var sendMouseEvent = function (vnc, event) {
    var outBuffer = new Buffer();

    outBuffer.appendBytes(5);     // type (u8 5)
    outBuffer.appendBytes(vnc.buttonMask);

    // position
    outBuffer.appendUint16(event.offsetX);
    outBuffer.appendUint16(event.offsetY);

    sendToWebSocket(vnc,outBuffer.read(outBuffer.length));
};





//Deals with specials keys
var getKeyupKeyDown = function(event) {
    var n = event.which;
    if (n >= "0".charCodeAt(0) && n <= "9".charCodeAt(0))
    	return n; 
    switch (n) {
        case 8:0xff96
            return 0xff08;      // backspace
        case 9:
            return 0xff09;      // tab
        case 13:
            return 0xff0d;      // return
        case 16:
            return 0xffe1;      // shift
        case 17:
            return 0xffe3;      // ctrl
        case 18:
        	return 0xffe9;		//left alt
        case 27:
        	return 0xff1b;		//escape
        case 32:
            return 32;          // space
        case 33:
        	return 0xff55;		//pageup
        case 34:
        	return 0xff56;		//pagedown
        case 35:
        	return 0xff57;		//end
        case 36:
        	return 0xff50;		//home
        case 37:				
        	return 0xff96;		//left
        case 38:				
        	return 0xff97;		//up
        case 39:				
        	return 0xff98;		//right
        case 40:				
        	return 0xff99;		//down
        case 45:
        	return 0xff63;		//insert
        case 46:
            return 0xffff;      // delete
        case 186:
            return 58;          // colon
        case 187:
            return 43;          // plus
        case 188:
            return 44;          // comma
        case 189:
            return 45;          // minus
        case 190:
            return 46;          // period
        case 191:
            return 47;          // slash
        case 219:
            return 91;          // left square bracket
        case 220:
            return 92;          // backslash
        case 221:
            return 93;          // right square bracket
        case 222:
            return 39;          // quote
        case 225:
        	return 0xffea;		//right alt
        default:
        	return -1;
    }
};

//Deals with alpha-numeric keys
var getKeypress = function(event) {
    var n = event.which;
    if ((n >= "A".charCodeAt(0) && n <= "Z".charCodeAt(0))||(n >= "a".charCodeAt(0) && n <= "z".charCodeAt(0)))
    	return n;  
    else
    	return -1;
};


Vnc.prototype.keyDownHandler = function(vnc, event) {
		var key = getKeyupKeyDown(event);
		if(key>0)
			sendKeyEvent(vnc, key, true);
		else{//IF not a special char activate keypress event(throwing an error!?)
			 throw new Error("keypress event");
		}
};

Vnc.prototype.keyUpHandler = function(vnc, event) {
		var key = getKeyupKeyDown(event);
		if(key>0)
			sendKeyEvent(vnc, key, false);
};
Vnc.prototype.keyPressHandler = function(vnc, event) {
	var key = getKeypress(event);
	if(key>0){
		sendKeyEvent(vnc, key, true);
		sendKeyEvent(vnc, key, false);
	}
};

var sendKeyEvent = function doKeyEvent(vnc, key, downFlag) {
    var outBuffer = new Buffer();

    outBuffer.appendBytes(4);     // type (u8 4)
    outBuffer.appendBytes(downFlag);
    outBuffer.appendBytes(0,0);   // padding

    outBuffer.appendUint32(key);

    sendToWebSocket(vnc,outBuffer.read(outBuffer.length));
};
//END FIXME

//Buffer object constructor
Buffer = function() {
    this.chunks = [];
    this.length = 0;
};



// Append an ArrayBuffer.
Buffer.prototype.append = function(bytes) {
    this.chunks.push(new Uint8Array(bytes));
    this.length += bytes.byteLength;
    return this;
};

Buffer.prototype.appendBytes = function() {
    ba = new Uint8Array(arguments);
    this.append(ba.buffer);
};

Buffer.prototype.appendUint16 = function(n) {
    var b = new ArrayBuffer(2);
    var dv = new DataView(b);
    dv.setUint16(0, n);
    this.append(b);
};

Buffer.prototype.appendUint32 = function(n) {
    var b = new ArrayBuffer(4);
    var dv = new DataView(b);
    dv.setUint32(0, n);
    this.append(b);
};

// Prepend an ArrayBuffer.
Buffer.prototype.prepend = function(b) {
    this.chunks.unshift(b);
    this.length += b.byteLength;
    return this;
};

Buffer.prototype.read = function(n) {
    if (n > this.length) {
        return null;
    }
    var result = new Uint8Array(n);
    var ri = 0;
    var scannedLength = 0;
    var chunks = this.chunks;
    // Skip over chunks until n or more bytes have been seen.
    for (var idx=0; true; idx++) {
        var chunk = chunks.shift();
        scannedLength += chunk.byteLength;
        if (scannedLength >= n){ 
            // Add partial array to result
            for (var i=0; ri<n; i++) {
                result[ri++] = chunk[i];
            }
            // Save unused remainer back into chunk array.
            var remainder = new Uint8Array(scannedLength - n);
            for (var j=0; i<chunk.byteLength; i++) {
                remainder[j++] = chunk[i];
            }
            chunks.unshift(remainder);
            break;
        } else {
            // Consume entire chunk.
            for (var i=0; i<chunk.byteLength; i++) {
                result[ri++] = chunk[i];
            }
        }
    }

    // Update length.
    this.length = this.length - n;
    // Return n leading bytes.
    return result.buffer;
};

Buffer.prototype.readUint8 = function(){
    var b = this.read(1);
    var dv = new DataView(b);
    return dv.getUint8(0);
};

Buffer.prototype.readUint16 = function(){
    var b = this.read(2);
    var dv = new DataView(b);
    return dv.getUint16(0);
};

Buffer.prototype.readUint32 = function() {
    var b = this.read(4);
    var dv = new DataView(b);
    return dv.getUint32(0);
};