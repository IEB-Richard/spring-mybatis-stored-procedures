package tk.mybatis.simple.mapper;

import java.util.List;

import tk.mybatis.simple.model.SysUser;

public interface UserMapper {
	/**
	 * Select all users, their roles and privilege
	 * @return
	 */
	List<SysUser> selectAllUserAndRolesSelect();
	
}
