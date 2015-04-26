package br.com.naegling.domain;

public enum NaeglingComType {
	START_NODE(1),
	STOP_NODE(2),
	CREATE_MASTER_NODE(3),
	CREATE_SLAVE_NODE(4),
	NODE_STATUS(5),
	TEMPLATE_STATUS(6),
	START_MASTER_VIRTUAL_NODE(7),
	STOP_MASTER_VIRTUAL_NODE(8),
    START_NEW_CLUSTER(9),
    ADD_WORKING_NODE(10),
    REMOVE_WORKING_NODE(11),
    GET_CLUSTER_STATUS(12),
    REQUEST_CLUSTER_IP(13),
    REQUEST_TEMPLATE_TRANSFER(14),
    REQUEST_JOB_TRANSFER(15),
    EXECUTE_JOB(16),
    DOWNLOAD_JOB_FILE(17),
    DELETE_NODE(18),
    EDIT_NODE(19);

	private int value;    

	  private NaeglingComType(int value) {
	    this.value = value;
	  }

	  public int getValue() {
	    return value;
	  }

}
