package cn.guwei.bos.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.guwei.bos.domain.user.User;
import cn.guwei.bos.service.facede.FacedeService;

/**
 * 连接数据库
 * @author vian
 *
 */
public class BOSRealm extends AuthorizingRealm {
	
	@Autowired
	private FacedeService facedeService;

	@Override//授权
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		
		return null;
	}

	@Override//认证
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken mytoken =  (UsernamePasswordToken) token;
		String email = mytoken.getUsername();
		User user = facedeService.getUserService().findUserByEmail(email);
		if (user==null) {
			return null;
		} else {
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), super.getName());
			return info;
		}
	}

}
