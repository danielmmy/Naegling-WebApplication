/**
 * 
 */

var bindHandlers = function(vnc) {
    var canvas = vnc.canvas;
    canvas.addEventListener("contextmenu", preventDefaultHandler, true);
    canvas.addEventListener("mousedown", mouseDownHandler, true);
    canvas.addEventListener("mouseup", mouseUpHandler, true);
    canvas.addEventListener("mousewheel", mouseWheelHandler, true);
    canvas.addEventListener("mousemove", mouseMoveHandler, true);
    document.addEventListener("keydown", keyDownHandler, false);
    document.addEventListener("keyup", keyUpHandler, false);
    document.addEventListener("keypress", keyPressHandler, false);
    
    
};

var preventDefaultHandler = function(e) {
    e.preventDefault();
    return false;
};

var mouseDownHandler = function(e) {
    vnc.mouseDownHandler(vnc, e);
    e.preventDefault();
    return false;
};

var mouseUpHandler = function(e) {
	
    vnc.mouseUpHandler(vnc, e);
    e.preventDefault();
    return false;
};

var mouseWheelHandler = function(e) {
    vnc.mouseWheelHandler(vnc, e);
    e.preventDefault();
};

var mouseMoveHandler = function(e) {
	vnc.mouseMoveHandler(vnc, e);
    e.preventDefault();
};

var keyDownHandler = function(event) {
	try{
		vnc.keyDownHandler(vnc, event);
		event.preventDefault();
		return false;
	}catch (e) {
		console.log("Keypress event");
	}
};

var keyUpHandler = function(event) {
    vnc.keyUpHandler(vnc, event);
    event.preventDefault();
    return false;
};

var keyPressHandler = function(event) {
    vnc.keyPressHandler(vnc, event);
    event.preventDefault();
    return false;
};




