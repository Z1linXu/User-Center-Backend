package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * User Name
     */
    private String username;

    /**
     * user account
     */
    private String userAccount;

    /**
     * User Avatar
     */
    private String avatarURL;

    /**
     * gender
     */
    private Integer gender;

    /**
     * password
     */
    private String userPassword;

    /**
     * phone number
     */
    private String phone;

    /**
     * email
     */
    private String email;

    /**
     * user status  0-normal
     */
    private Integer userStatus;

    /**
     * created time
     */
    private Date creatTime;

    /**
     * update time
     */
    private Date updateTime;

    /**
     * Delete or not
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}