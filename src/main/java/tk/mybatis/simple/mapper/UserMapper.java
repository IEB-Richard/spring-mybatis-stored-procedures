package tk.mybatis.simple.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.simple.model.SysUser;

public interface UserMapper {
	/**
	 * Select all users, their roles and privilege
	 * @return
	 */
	List<SysUser> selectAllUserAndRolesSelect();
	/**
	 * 
	 * @param user
	 */
	void selectUserbyId(SysUser user);
	/**
	 * 
	 * @param params
	 * @return
	 */
	List<SysUser> selectUserPage(Map<String, Object> params);
	
	/**
	 * 
	 * @param user
	 * @param roleIds
	 * @return
	 */
	int insertUserAndRoles(
			@Param("user") SysUser user, 
			@Param("roleIds") String roleIds);
	/**
	 * 
	 * @param id
	 * @return
	 */
	int deleteUserById(Long id);
}
