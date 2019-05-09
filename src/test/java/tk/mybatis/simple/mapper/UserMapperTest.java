package tk.mybatis.simple.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import tk.mybatis.simple.model.SysPrivilege;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

public class UserMapperTest extends BaseMapperTest {

	public void testSelectUserAndRoleByIdSelect() {
		// get SqlSession
		SqlSession sqlSession = getSqlSession();
		try {
			// get UserMapper object do search action
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysUser> userList = userMapper.selectAllUserAndRolesSelect();

			// Check results
			System.out.println("No. of users: " + userList.size());
			for (SysUser user : userList) {
				System.out.println("User name: " + user.getUserName());
				for (SysRole role : user.getRoleList()) {
					System.out.println("Role name: " + role.getRoleName());
					for (SysPrivilege privilege : role.getPrivilegeList()) {
						System.out.println("Privlilege name: " + privilege.getPrivilegeName());
					}
				}
			}
		} finally {
			// Please don't forget to close the session after reach test
			sqlSession.close();
		}
	}

	public void testSelectByUserId() {
		SqlSession sqlSession = getSqlSession();
		try {
			// Get the UserMapper interface
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setId(1L);
			userMapper.selectUserbyId(user);
			Assert.assertNotNull(user.getUserName());
			System.out.println("User name: " + user.getUserName());
		} finally {
			// Please don't really change database data, that will affect other tests
			sqlSession.rollback();
			// Please close the SQL session after each test case.
			sqlSession.close();
		}
	}

	@Test
	public void testSelectUserPage() {
		SqlSession sqlSession = getSqlSession();
		try {
			// Get the UserMapper interface
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userName", "ad");
			params.put("offset", 0);
			params.put("limit", 10);
			List<SysUser> userList = userMapper.selectUserPage(params);
			Long total = (Long) params.get("total");
			System.out.println("Total No.:" + total);
			for (SysUser user : userList) {
				System.out.println("User name: " + user.getUserName());
			}
		} finally {
			// Please close the SQL session after each test case.
			sqlSession.close();
		}
	}

	@Test
	public void testInsertUserAndRolesAndDelete() {
		SqlSession sqlSession = getSqlSession();
		try {
			// Get the UserMapper interface
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

			SysUser user = new SysUser();
			user.setUserName("test");
			user.setUserPassword("123456");
			user.setUserEmail("test@mybatis.tk");
			user.setUserInfo("test info");
			user.setHeadImg(new byte[] { 1, 2, 3 });
			
			// insert user information and roles info.
			userMapper.insertUserAndRoles(user, "1,2");
			Assert.assertNotNull(user.getId());
			Assert.assertNotNull(user.getCreateTime());
			// commit and then check the data from database
			sqlSession.commit();
			// then delete the user we just created.
			userMapper.deleteUserById(user.getId());
			sqlSession.commit();
		} finally {
			// Please close the SQL session after each test case.
			sqlSession.close();
		}
	}

}
