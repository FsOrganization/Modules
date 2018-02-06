
package org.sword.wechat4j.token.server;
import org.sword.wechat4j.common.Config;
import org.sword.wechat4j.token.AccessToken;

public class AccessTokenServer extends AbsServer implements TokenServer {
	private int requestTimes = 5;//token请求失败后重新请求的次数
	private AccessToken accessToken = new AccessToken();
	public String token(){
		return super.token();
	}

	@Override
	protected String getCustomerServerClass() {
		return Config.instance().getAccessTokenServer();
	}

	@Override
	public IServer defaultServer() {
		return AccessTokenMemServer.instance();
	}
	
	/**
	 * 服务器刷新token
	 */
	private void refresh(){
		for(int i=0;i<requestTimes;i++){
			//请求成功则退出
			if(this.accessToken.request())
				break;
		}
	}

}
