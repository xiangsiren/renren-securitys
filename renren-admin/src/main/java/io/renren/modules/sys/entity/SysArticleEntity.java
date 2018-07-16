package io.renren.modules.sys.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 文章表
 *
 * @since 3.1.2 2018-6-2
 */
@TableName("sys_article")
public class SysArticleEntity  implements Serializable {
    public static final long serialVersionUID = 1L ;

    @TableId
    private Long id ;

    private Long cid ;

    @NotNull(message = "标题非空")
    private String title ;

    private String  content ;

    private String note ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
