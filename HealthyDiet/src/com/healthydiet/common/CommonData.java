package com.healthydiet.common;


public class CommonData {

	public static final boolean DEBUG = true;
	public static final boolean ISRELEASE_URL = true;

	private static final String SEVER_HOST = ISRELEASE_URL ? "http://api.yi18.net/" : "http://api.yi18.net/";// 服务端地址
	public static final String SEVER_URL = SEVER_HOST + "ws/handler/";
	public static final String SOCKET_HOST = ISRELEASE_URL ? "192.168.1.200" : "192.168.1.200";// 聊天地址
	public static final int SOCKET_PORT = ISRELEASE_URL ? 8099 : 8099;// 聊天端口
	
	public static final String PARAM_JSONSTR = "jsonStr";
	public static final String NETWORK_ERROR = "网络异常，请稍后重试";
	
	public enum ResultCode {
        SUCCESS(0), FAIL(-1), ERROR(-2);

        public final int value;

        private ResultCode(int value) {
            this.value = value;
        }
    }
	
	public enum IMType {
		CONN(1), CHAT(2), CHATROOM(3), PAL(4), PALRES(5), EXIT(6);
		
		public final int value;
		
		private IMType(int value) {
			this.value = value;
		}
	}

	public static int userid;

	public static final String SPREFERENCES_NAME = "sp_info";
}