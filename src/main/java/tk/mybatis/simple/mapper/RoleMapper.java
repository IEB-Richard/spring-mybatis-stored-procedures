package tk.mybatis.simple.mapper;

import java.util.List;

import tk.mybatis.simple.model.SysRole;

public interface RoleMapper {
	SysRole selectById(Long id);
	List<SysRole> selectRoleByUserId(Long userId);
	List<SysRole> selectRoleByUserIdChoose(Long userId);	
	List<SysRole> rolePrivilegeListMapChoose(Long userId);
	int updateById(SysRole role);
}
