package dit.crush.ditcrush.mapper;

import dit.crush.ditcrush.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDataMapper {
    @Insert("insert into userdata (account_id,name,token,gmt_create,gmt_modified) values (#{account_id},#{name},#{token},#{gmt_create},#{gmt_modified})")
    void insert(User user);

}
