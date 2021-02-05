package socket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;


/**
 * @author 寒洲
 * @description 用于从websocket中获取用户session
 * @date 2020/11/18
 */
public class HttpSessionConfigurator extends ServerEndpointConfig.Configurator  {

	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		// 获取WebSocket的httpSession
		HttpSession httpSession = (HttpSession) request.getHttpSession();
		// 储存到ServerEndpointConfig中，在Endpoint中可以通过EndpointConfig获取储存的内容
		sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
	}
}
