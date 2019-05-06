package tk.mybatis.simple.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import tk.mybatis.simple.model.SysPrivilege;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

public class UserMapperTest extends BaseMapperTest {

	@Test
	public void testSelectUserAndRoleByIdSelect() {
		// get SqlSession
		SqlSession sqlSession = getSqlSession();
		try {
			// get UserMapper object do search action
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysUser> userList = userMapper.selectAllUserAndRolesSelect();
			
			// Check results
			System.out.println("No. of users: " + userList.size());
			for(SysUser user: userList) {
				System.out.println("User name: " + user.getUserName());
				for(SysRole role: user.getRoleList()) {
					System.out.println("Role name: " + role.getRoleName());
					for(SysPrivilege privilege: role.getPrivilegeList()) {
						System.out.println("Privlilege name: " + privilege.getPrivilegeName());
					}
				}
			}
		} finally {
			// Please don't forget to close the session after reach test
			sqlSession.close();
		}
	}

}
