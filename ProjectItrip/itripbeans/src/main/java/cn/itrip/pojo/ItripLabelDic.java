package cn.itrip.pojo;
import java.io.Serializable;
import java.util.Date;
/***
*   标签字典表
*/
public class ItripLabelDic implements Serializable {
        //主键
        private Long id;
        //key值
        private String name;
        //value值
        private String value;
        //描述
        private String description;
        //父级标签id(1级标签则为0)
        private Long parentId;
        //标签图片地址
        private String pic;
        //
        private Date creationDate;
        //
        private Long createdBy;
        //
        private Date modifyDate;
        //
        private Long modifiedBy;
        //get set 方法
            public void setId (Long  id){
                this.id=id;
            }
            public  Long getId(){
                return this.id;
            }
            public void setName (String  name){
                this.name=name;
            }
            public  String getName(){
                return this.name;
            }
            public void setValue (String  value){
                this.value=value;
            }
            public  String getValue(){
                return this.value;
            }
            public void setDescription (String  description){
                this.description=description;
            }
            public  String getDescription(){
                return this.description;
            }
            public void setParentId (Long  parentId){
                this.parentId=parentId;
            }
            public  Long getParentId(){
                return this.parentId;
            }
            public void setPic (String  pic){
                this.pic=pic;
            }
            public  String getPic(){
                return this.pic;
            }
            public void setCreationDate (Date  creationDate){
                this.creationDate=creationDate;
            }
            public  Date getCreationDate(){
                return this.creationDate;
            }
            public void setCreatedBy (Long  createdBy){
                this.createdBy=createdBy;
            }
            public  Long getCreatedBy(){
                return this.createdBy;
            }
            public void setModifyDate (Date  modifyDate){
                this.modifyDate=modifyDate;
            }
            public  Date getModifyDate(){
                return this.modifyDate;
            }
            public void setModifiedBy (Long  modifiedBy){
                this.modifiedBy=modifiedBy;
            }
            public  Long getModifiedBy(){
                return this.modifiedBy;
            }
}
