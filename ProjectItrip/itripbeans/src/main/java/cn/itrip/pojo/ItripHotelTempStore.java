package cn.itrip.pojo;
import java.io.Serializable;
import java.util.Date;
/***
*   实时库存表
*/
public class ItripHotelTempStore implements Serializable {
        //
        private Long id;
        //
        private Integer hotelId;
        //商品id
        private Long roomId;
        //记录时间
        private Date recordDate;
        //库存
        private Integer store;
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
            public void setHotelId (Integer  hotelId){
                this.hotelId=hotelId;
            }
            public  Integer getHotelId(){
                return this.hotelId;
            }
            public void setRoomId (Long  roomId){
                this.roomId=roomId;
            }
            public  Long getRoomId(){
                return this.roomId;
            }
            public void setRecordDate (Date  recordDate){
                this.recordDate=recordDate;
            }
            public  Date getRecordDate(){
                return this.recordDate;
            }
            public void setStore (Integer  store){
                this.store=store;
            }
            public  Integer getStore(){
                return this.store;
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
