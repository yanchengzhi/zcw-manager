package com.ycz.zcw.manager.pojo;

import java.util.ArrayList;
import java.util.List;

public class UserTokenExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserTokenExample() {
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
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andPasswordTokenIsNull() {
            addCriterion("password_token is null");
            return (Criteria) this;
        }

        public Criteria andPasswordTokenIsNotNull() {
            addCriterion("password_token is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordTokenEqualTo(String value) {
            addCriterion("password_token =", value, "passwordToken");
            return (Criteria) this;
        }

        public Criteria andPasswordTokenNotEqualTo(String value) {
            addCriterion("password_token <>", value, "passwordToken");
            return (Criteria) this;
        }

        public Criteria andPasswordTokenGreaterThan(String value) {
            addCriterion("password_token >", value, "passwordToken");
            return (Criteria) this;
        }

        public Criteria andPasswordTokenGreaterThanOrEqualTo(String value) {
            addCriterion("password_token >=", value, "passwordToken");
            return (Criteria) this;
        }

        public Criteria andPasswordTokenLessThan(String value) {
            addCriterion("password_token <", value, "passwordToken");
            return (Criteria) this;
        }

        public Criteria andPasswordTokenLessThanOrEqualTo(String value) {
            addCriterion("password_token <=", value, "passwordToken");
            return (Criteria) this;
        }

        public Criteria andPasswordTokenLike(String value) {
            addCriterion("password_token like", value, "passwordToken");
            return (Criteria) this;
        }

        public Criteria andPasswordTokenNotLike(String value) {
            addCriterion("password_token not like", value, "passwordToken");
            return (Criteria) this;
        }

        public Criteria andPasswordTokenIn(List<String> values) {
            addCriterion("password_token in", values, "passwordToken");
            return (Criteria) this;
        }

        public Criteria andPasswordTokenNotIn(List<String> values) {
            addCriterion("password_token not in", values, "passwordToken");
            return (Criteria) this;
        }

        public Criteria andPasswordTokenBetween(String value1, String value2) {
            addCriterion("password_token between", value1, value2, "passwordToken");
            return (Criteria) this;
        }

        public Criteria andPasswordTokenNotBetween(String value1, String value2) {
            addCriterion("password_token not between", value1, value2, "passwordToken");
            return (Criteria) this;
        }

        public Criteria andAutoLoginTokenIsNull() {
            addCriterion("auto_login_token is null");
            return (Criteria) this;
        }

        public Criteria andAutoLoginTokenIsNotNull() {
            addCriterion("auto_login_token is not null");
            return (Criteria) this;
        }

        public Criteria andAutoLoginTokenEqualTo(String value) {
            addCriterion("auto_login_token =", value, "autoLoginToken");
            return (Criteria) this;
        }

        public Criteria andAutoLoginTokenNotEqualTo(String value) {
            addCriterion("auto_login_token <>", value, "autoLoginToken");
            return (Criteria) this;
        }

        public Criteria andAutoLoginTokenGreaterThan(String value) {
            addCriterion("auto_login_token >", value, "autoLoginToken");
            return (Criteria) this;
        }

        public Criteria andAutoLoginTokenGreaterThanOrEqualTo(String value) {
            addCriterion("auto_login_token >=", value, "autoLoginToken");
            return (Criteria) this;
        }

        public Criteria andAutoLoginTokenLessThan(String value) {
            addCriterion("auto_login_token <", value, "autoLoginToken");
            return (Criteria) this;
        }

        public Criteria andAutoLoginTokenLessThanOrEqualTo(String value) {
            addCriterion("auto_login_token <=", value, "autoLoginToken");
            return (Criteria) this;
        }

        public Criteria andAutoLoginTokenLike(String value) {
            addCriterion("auto_login_token like", value, "autoLoginToken");
            return (Criteria) this;
        }

        public Criteria andAutoLoginTokenNotLike(String value) {
            addCriterion("auto_login_token not like", value, "autoLoginToken");
            return (Criteria) this;
        }

        public Criteria andAutoLoginTokenIn(List<String> values) {
            addCriterion("auto_login_token in", values, "autoLoginToken");
            return (Criteria) this;
        }

        public Criteria andAutoLoginTokenNotIn(List<String> values) {
            addCriterion("auto_login_token not in", values, "autoLoginToken");
            return (Criteria) this;
        }

        public Criteria andAutoLoginTokenBetween(String value1, String value2) {
            addCriterion("auto_login_token between", value1, value2, "autoLoginToken");
            return (Criteria) this;
        }

        public Criteria andAutoLoginTokenNotBetween(String value1, String value2) {
            addCriterion("auto_login_token not between", value1, value2, "autoLoginToken");
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