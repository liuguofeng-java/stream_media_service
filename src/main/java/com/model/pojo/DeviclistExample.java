package com.model.pojo;

import java.util.ArrayList;
import java.util.List;

public class DeviclistExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DeviclistExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("Id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("Id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("Id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("Id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("Id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("Id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("Id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("Id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("Id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("Id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("Id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("Id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDevicidIsNull() {
            addCriterion("DevicId is null");
            return (Criteria) this;
        }

        public Criteria andDevicidIsNotNull() {
            addCriterion("DevicId is not null");
            return (Criteria) this;
        }

        public Criteria andDevicidEqualTo(String value) {
            addCriterion("DevicId =", value, "devicid");
            return (Criteria) this;
        }

        public Criteria andDevicidNotEqualTo(String value) {
            addCriterion("DevicId <>", value, "devicid");
            return (Criteria) this;
        }

        public Criteria andDevicidGreaterThan(String value) {
            addCriterion("DevicId >", value, "devicid");
            return (Criteria) this;
        }

        public Criteria andDevicidGreaterThanOrEqualTo(String value) {
            addCriterion("DevicId >=", value, "devicid");
            return (Criteria) this;
        }

        public Criteria andDevicidLessThan(String value) {
            addCriterion("DevicId <", value, "devicid");
            return (Criteria) this;
        }

        public Criteria andDevicidLessThanOrEqualTo(String value) {
            addCriterion("DevicId <=", value, "devicid");
            return (Criteria) this;
        }

        public Criteria andDevicidLike(String value) {
            addCriterion("DevicId like", value, "devicid");
            return (Criteria) this;
        }

        public Criteria andDevicidNotLike(String value) {
            addCriterion("DevicId not like", value, "devicid");
            return (Criteria) this;
        }

        public Criteria andDevicidIn(List<String> values) {
            addCriterion("DevicId in", values, "devicid");
            return (Criteria) this;
        }

        public Criteria andDevicidNotIn(List<String> values) {
            addCriterion("DevicId not in", values, "devicid");
            return (Criteria) this;
        }

        public Criteria andDevicidBetween(String value1, String value2) {
            addCriterion("DevicId between", value1, value2, "devicid");
            return (Criteria) this;
        }

        public Criteria andDevicidNotBetween(String value1, String value2) {
            addCriterion("DevicId not between", value1, value2, "devicid");
            return (Criteria) this;
        }

        public Criteria andLuseridIsNull() {
            addCriterion("LuserID is null");
            return (Criteria) this;
        }

        public Criteria andLuseridIsNotNull() {
            addCriterion("LuserID is not null");
            return (Criteria) this;
        }

        public Criteria andLuseridEqualTo(Integer value) {
            addCriterion("LuserID =", value, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridNotEqualTo(Integer value) {
            addCriterion("LuserID <>", value, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridGreaterThan(Integer value) {
            addCriterion("LuserID >", value, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("LuserID >=", value, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridLessThan(Integer value) {
            addCriterion("LuserID <", value, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridLessThanOrEqualTo(Integer value) {
            addCriterion("LuserID <=", value, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridIn(List<Integer> values) {
            addCriterion("LuserID in", values, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridNotIn(List<Integer> values) {
            addCriterion("LuserID not in", values, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridBetween(Integer value1, Integer value2) {
            addCriterion("LuserID between", value1, value2, "luserid");
            return (Criteria) this;
        }

        public Criteria andLuseridNotBetween(Integer value1, Integer value2) {
            addCriterion("LuserID not between", value1, value2, "luserid");
            return (Criteria) this;
        }

        public Criteria andSessionidIsNull() {
            addCriterion("SessionId is null");
            return (Criteria) this;
        }

        public Criteria andSessionidIsNotNull() {
            addCriterion("SessionId is not null");
            return (Criteria) this;
        }

        public Criteria andSessionidEqualTo(Integer value) {
            addCriterion("SessionId =", value, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidNotEqualTo(Integer value) {
            addCriterion("SessionId <>", value, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidGreaterThan(Integer value) {
            addCriterion("SessionId >", value, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidGreaterThanOrEqualTo(Integer value) {
            addCriterion("SessionId >=", value, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidLessThan(Integer value) {
            addCriterion("SessionId <", value, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidLessThanOrEqualTo(Integer value) {
            addCriterion("SessionId <=", value, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidIn(List<Integer> values) {
            addCriterion("SessionId in", values, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidNotIn(List<Integer> values) {
            addCriterion("SessionId not in", values, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidBetween(Integer value1, Integer value2) {
            addCriterion("SessionId between", value1, value2, "sessionid");
            return (Criteria) this;
        }

        public Criteria andSessionidNotBetween(Integer value1, Integer value2) {
            addCriterion("SessionId not between", value1, value2, "sessionid");
            return (Criteria) this;
        }

        public Criteria andPreviewhandleIsNull() {
            addCriterion("PreviewHandle is null");
            return (Criteria) this;
        }

        public Criteria andPreviewhandleIsNotNull() {
            addCriterion("PreviewHandle is not null");
            return (Criteria) this;
        }

        public Criteria andPreviewhandleEqualTo(Integer value) {
            addCriterion("PreviewHandle =", value, "previewhandle");
            return (Criteria) this;
        }

        public Criteria andPreviewhandleNotEqualTo(Integer value) {
            addCriterion("PreviewHandle <>", value, "previewhandle");
            return (Criteria) this;
        }

        public Criteria andPreviewhandleGreaterThan(Integer value) {
            addCriterion("PreviewHandle >", value, "previewhandle");
            return (Criteria) this;
        }

        public Criteria andPreviewhandleGreaterThanOrEqualTo(Integer value) {
            addCriterion("PreviewHandle >=", value, "previewhandle");
            return (Criteria) this;
        }

        public Criteria andPreviewhandleLessThan(Integer value) {
            addCriterion("PreviewHandle <", value, "previewhandle");
            return (Criteria) this;
        }

        public Criteria andPreviewhandleLessThanOrEqualTo(Integer value) {
            addCriterion("PreviewHandle <=", value, "previewhandle");
            return (Criteria) this;
        }

        public Criteria andPreviewhandleIn(List<Integer> values) {
            addCriterion("PreviewHandle in", values, "previewhandle");
            return (Criteria) this;
        }

        public Criteria andPreviewhandleNotIn(List<Integer> values) {
            addCriterion("PreviewHandle not in", values, "previewhandle");
            return (Criteria) this;
        }

        public Criteria andPreviewhandleBetween(Integer value1, Integer value2) {
            addCriterion("PreviewHandle between", value1, value2, "previewhandle");
            return (Criteria) this;
        }

        public Criteria andPreviewhandleNotBetween(Integer value1, Integer value2) {
            addCriterion("PreviewHandle not between", value1, value2, "previewhandle");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNull() {
            addCriterion("OrderNo is null");
            return (Criteria) this;
        }

        public Criteria andOrdernoIsNotNull() {
            addCriterion("OrderNo is not null");
            return (Criteria) this;
        }

        public Criteria andOrdernoEqualTo(String value) {
            addCriterion("OrderNo =", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotEqualTo(String value) {
            addCriterion("OrderNo <>", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThan(String value) {
            addCriterion("OrderNo >", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoGreaterThanOrEqualTo(String value) {
            addCriterion("OrderNo >=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThan(String value) {
            addCriterion("OrderNo <", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLessThanOrEqualTo(String value) {
            addCriterion("OrderNo <=", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoLike(String value) {
            addCriterion("OrderNo like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotLike(String value) {
            addCriterion("OrderNo not like", value, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoIn(List<String> values) {
            addCriterion("OrderNo in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotIn(List<String> values) {
            addCriterion("OrderNo not in", values, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoBetween(String value1, String value2) {
            addCriterion("OrderNo between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andOrdernoNotBetween(String value1, String value2) {
            addCriterion("OrderNo not between", value1, value2, "orderno");
            return (Criteria) this;
        }

        public Criteria andVideopathIsNull() {
            addCriterion("VideoPath is null");
            return (Criteria) this;
        }

        public Criteria andVideopathIsNotNull() {
            addCriterion("VideoPath is not null");
            return (Criteria) this;
        }

        public Criteria andVideopathEqualTo(String value) {
            addCriterion("VideoPath =", value, "videopath");
            return (Criteria) this;
        }

        public Criteria andVideopathNotEqualTo(String value) {
            addCriterion("VideoPath <>", value, "videopath");
            return (Criteria) this;
        }

        public Criteria andVideopathGreaterThan(String value) {
            addCriterion("VideoPath >", value, "videopath");
            return (Criteria) this;
        }

        public Criteria andVideopathGreaterThanOrEqualTo(String value) {
            addCriterion("VideoPath >=", value, "videopath");
            return (Criteria) this;
        }

        public Criteria andVideopathLessThan(String value) {
            addCriterion("VideoPath <", value, "videopath");
            return (Criteria) this;
        }

        public Criteria andVideopathLessThanOrEqualTo(String value) {
            addCriterion("VideoPath <=", value, "videopath");
            return (Criteria) this;
        }

        public Criteria andVideopathLike(String value) {
            addCriterion("VideoPath like", value, "videopath");
            return (Criteria) this;
        }

        public Criteria andVideopathNotLike(String value) {
            addCriterion("VideoPath not like", value, "videopath");
            return (Criteria) this;
        }

        public Criteria andVideopathIn(List<String> values) {
            addCriterion("VideoPath in", values, "videopath");
            return (Criteria) this;
        }

        public Criteria andVideopathNotIn(List<String> values) {
            addCriterion("VideoPath not in", values, "videopath");
            return (Criteria) this;
        }

        public Criteria andVideopathBetween(String value1, String value2) {
            addCriterion("VideoPath between", value1, value2, "videopath");
            return (Criteria) this;
        }

        public Criteria andVideopathNotBetween(String value1, String value2) {
            addCriterion("VideoPath not between", value1, value2, "videopath");
            return (Criteria) this;
        }

        public Criteria andStreampathIsNull() {
            addCriterion("StreamPath is null");
            return (Criteria) this;
        }

        public Criteria andStreampathIsNotNull() {
            addCriterion("StreamPath is not null");
            return (Criteria) this;
        }

        public Criteria andStreampathEqualTo(String value) {
            addCriterion("StreamPath =", value, "streampath");
            return (Criteria) this;
        }

        public Criteria andStreampathNotEqualTo(String value) {
            addCriterion("StreamPath <>", value, "streampath");
            return (Criteria) this;
        }

        public Criteria andStreampathGreaterThan(String value) {
            addCriterion("StreamPath >", value, "streampath");
            return (Criteria) this;
        }

        public Criteria andStreampathGreaterThanOrEqualTo(String value) {
            addCriterion("StreamPath >=", value, "streampath");
            return (Criteria) this;
        }

        public Criteria andStreampathLessThan(String value) {
            addCriterion("StreamPath <", value, "streampath");
            return (Criteria) this;
        }

        public Criteria andStreampathLessThanOrEqualTo(String value) {
            addCriterion("StreamPath <=", value, "streampath");
            return (Criteria) this;
        }

        public Criteria andStreampathLike(String value) {
            addCriterion("StreamPath like", value, "streampath");
            return (Criteria) this;
        }

        public Criteria andStreampathNotLike(String value) {
            addCriterion("StreamPath not like", value, "streampath");
            return (Criteria) this;
        }

        public Criteria andStreampathIn(List<String> values) {
            addCriterion("StreamPath in", values, "streampath");
            return (Criteria) this;
        }

        public Criteria andStreampathNotIn(List<String> values) {
            addCriterion("StreamPath not in", values, "streampath");
            return (Criteria) this;
        }

        public Criteria andStreampathBetween(String value1, String value2) {
            addCriterion("StreamPath between", value1, value2, "streampath");
            return (Criteria) this;
        }

        public Criteria andStreampathNotBetween(String value1, String value2) {
            addCriterion("StreamPath not between", value1, value2, "streampath");
            return (Criteria) this;
        }

        public Criteria andAddtimeIsNull() {
            addCriterion("AddTime is null");
            return (Criteria) this;
        }

        public Criteria andAddtimeIsNotNull() {
            addCriterion("AddTime is not null");
            return (Criteria) this;
        }

        public Criteria andAddtimeEqualTo(String value) {
            addCriterion("AddTime =", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeNotEqualTo(String value) {
            addCriterion("AddTime <>", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeGreaterThan(String value) {
            addCriterion("AddTime >", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeGreaterThanOrEqualTo(String value) {
            addCriterion("AddTime >=", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeLessThan(String value) {
            addCriterion("AddTime <", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeLessThanOrEqualTo(String value) {
            addCriterion("AddTime <=", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeLike(String value) {
            addCriterion("AddTime like", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeNotLike(String value) {
            addCriterion("AddTime not like", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeIn(List<String> values) {
            addCriterion("AddTime in", values, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeNotIn(List<String> values) {
            addCriterion("AddTime not in", values, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeBetween(String value1, String value2) {
            addCriterion("AddTime between", value1, value2, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeNotBetween(String value1, String value2) {
            addCriterion("AddTime not between", value1, value2, "addtime");
            return (Criteria) this;
        }

        public Criteria andPidIsNull() {
            addCriterion("Pid is null");
            return (Criteria) this;
        }

        public Criteria andPidIsNotNull() {
            addCriterion("Pid is not null");
            return (Criteria) this;
        }

        public Criteria andPidEqualTo(Integer value) {
            addCriterion("Pid =", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotEqualTo(Integer value) {
            addCriterion("Pid <>", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThan(Integer value) {
            addCriterion("Pid >", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualTo(Integer value) {
            addCriterion("Pid >=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThan(Integer value) {
            addCriterion("Pid <", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualTo(Integer value) {
            addCriterion("Pid <=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidIn(List<Integer> values) {
            addCriterion("Pid in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotIn(List<Integer> values) {
            addCriterion("Pid not in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidBetween(Integer value1, Integer value2) {
            addCriterion("Pid between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotBetween(Integer value1, Integer value2) {
            addCriterion("Pid not between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andIsrunmp4IsNull() {
            addCriterion("IsRunMp4 is null");
            return (Criteria) this;
        }

        public Criteria andIsrunmp4IsNotNull() {
            addCriterion("IsRunMp4 is not null");
            return (Criteria) this;
        }

        public Criteria andIsrunmp4EqualTo(Integer value) {
            addCriterion("IsRunMp4 =", value, "isrunmp4");
            return (Criteria) this;
        }

        public Criteria andIsrunmp4NotEqualTo(Integer value) {
            addCriterion("IsRunMp4 <>", value, "isrunmp4");
            return (Criteria) this;
        }

        public Criteria andIsrunmp4GreaterThan(Integer value) {
            addCriterion("IsRunMp4 >", value, "isrunmp4");
            return (Criteria) this;
        }

        public Criteria andIsrunmp4GreaterThanOrEqualTo(Integer value) {
            addCriterion("IsRunMp4 >=", value, "isrunmp4");
            return (Criteria) this;
        }

        public Criteria andIsrunmp4LessThan(Integer value) {
            addCriterion("IsRunMp4 <", value, "isrunmp4");
            return (Criteria) this;
        }

        public Criteria andIsrunmp4LessThanOrEqualTo(Integer value) {
            addCriterion("IsRunMp4 <=", value, "isrunmp4");
            return (Criteria) this;
        }

        public Criteria andIsrunmp4In(List<Integer> values) {
            addCriterion("IsRunMp4 in", values, "isrunmp4");
            return (Criteria) this;
        }

        public Criteria andIsrunmp4NotIn(List<Integer> values) {
            addCriterion("IsRunMp4 not in", values, "isrunmp4");
            return (Criteria) this;
        }

        public Criteria andIsrunmp4Between(Integer value1, Integer value2) {
            addCriterion("IsRunMp4 between", value1, value2, "isrunmp4");
            return (Criteria) this;
        }

        public Criteria andIsrunmp4NotBetween(Integer value1, Integer value2) {
            addCriterion("IsRunMp4 not between", value1, value2, "isrunmp4");
            return (Criteria) this;
        }

        public Criteria andIsrunstreamIsNull() {
            addCriterion("IsRunStream is null");
            return (Criteria) this;
        }

        public Criteria andIsrunstreamIsNotNull() {
            addCriterion("IsRunStream is not null");
            return (Criteria) this;
        }

        public Criteria andIsrunstreamEqualTo(Integer value) {
            addCriterion("IsRunStream =", value, "isrunstream");
            return (Criteria) this;
        }

        public Criteria andIsrunstreamNotEqualTo(Integer value) {
            addCriterion("IsRunStream <>", value, "isrunstream");
            return (Criteria) this;
        }

        public Criteria andIsrunstreamGreaterThan(Integer value) {
            addCriterion("IsRunStream >", value, "isrunstream");
            return (Criteria) this;
        }

        public Criteria andIsrunstreamGreaterThanOrEqualTo(Integer value) {
            addCriterion("IsRunStream >=", value, "isrunstream");
            return (Criteria) this;
        }

        public Criteria andIsrunstreamLessThan(Integer value) {
            addCriterion("IsRunStream <", value, "isrunstream");
            return (Criteria) this;
        }

        public Criteria andIsrunstreamLessThanOrEqualTo(Integer value) {
            addCriterion("IsRunStream <=", value, "isrunstream");
            return (Criteria) this;
        }

        public Criteria andIsrunstreamIn(List<Integer> values) {
            addCriterion("IsRunStream in", values, "isrunstream");
            return (Criteria) this;
        }

        public Criteria andIsrunstreamNotIn(List<Integer> values) {
            addCriterion("IsRunStream not in", values, "isrunstream");
            return (Criteria) this;
        }

        public Criteria andIsrunstreamBetween(Integer value1, Integer value2) {
            addCriterion("IsRunStream between", value1, value2, "isrunstream");
            return (Criteria) this;
        }

        public Criteria andIsrunstreamNotBetween(Integer value1, Integer value2) {
            addCriterion("IsRunStream not between", value1, value2, "isrunstream");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}