package com.jimei.xiaolumeimei.entities;

import java.util.List;

/**
 * Created by wisdom on 16/8/22.
 */
public class WeekTaskRewardBean {
    @Override
    public String toString() {
        return "WeekTaskRewardBean{" +
                "staging_award_amount=" + staging_award_amount +
                ", staging_award_count=" + staging_award_count +
                ", personal_missions=" + personal_missions +
                ", group_missions=" + group_missions +
                '}';
    }

    /**
     * staging_award_amount : 10
     * personal_missions : [{"id":3,"mission":{"id":1,"name":"新增下属妈妈","kpi_type":"count","target_value":1,"award_amount":300},"mama_id":44,"year_week":"2016-34","finish_value":0,"finish_time":null,"status":"staging","status_name":"进行中"},{"id":5,"mission":{"id":3,"name":"引导新手妈妈完成任务","kpi_type":"count","target_value":8,"award_amount":800},"mama_id":44,"year_week":"2016-34","finish_value":0,"finish_time":null,"status":"staging","status_name":"进行中"},{"id":6,"mission":{"id":4,"name":"首单奖励","kpi_type":"count","target_value":1,"award_amount":50},"mama_id":44,"year_week":"2016-34","finish_value":0,"finish_time":null,"status":"staging","status_name":"进行中"},{"id":7,"mission":{"id":5,"name":"邀请妈妈一元开店","kpi_type":"count","target_value":1,"award_amount":50},"mama_id":44,"year_week":"2016-34","finish_value":0,"finish_time":null,"status":"staging","status_name":"进行中"},{"id":8,"mission":{"id":6,"name":"妈妈首次开讲","kpi_type":"count","target_value":1,"award_amount":300},"mama_id":44,"year_week":"2016-34","finish_value":0,"finish_time":null,"status":"staging","status_name":"进行中"},{"id":9,"mission":{"id":7,"name":"周销售任务","kpi_type":"amount","target_value":300,"award_amount":150},"mama_id":44,"year_week":"2016-34","finish_value":0,"finish_time":null,"status":"staging","status_name":"进行中"}]
     * staging_award_count : 8
     * group_missions : [{"id":4,"mission":{"id":2,"name":"新增团队妈妈","kpi_type":"count","target_value":1,"award_amount":100},"mama_id":44,"year_week":"2016-34","finish_value":0,"finish_time":null,"status":"staging","status_name":"进行中"},{"id":10,"mission":{"id":8,"name":"周团队销售目标","kpi_type":"count","target_value":300,"award_amount":1500},"mama_id":44,"year_week":"2016-34","finish_value":0,"finish_time":null,"status":"staging","status_name":"进行中"}]
     */

    private int staging_award_amount;
    private int staging_award_count;
    /**
     * id : 3
     * mission : {"id":1,"name":"新增下属妈妈","kpi_type":"count","target_value":1,"award_amount":300}
     * mama_id : 44
     * year_week : 2016-34
     * finish_value : 0
     * finish_time : null
     * status : staging
     * status_name : 进行中
     */

    private List<PersonalMissionsBean> personal_missions;
    /**
     * id : 4
     * mission : {"id":2,"name":"新增团队妈妈","kpi_type":"count","target_value":1,"award_amount":100}
     * mama_id : 44
     * year_week : 2016-34
     * finish_value : 0
     * finish_time : null
     * status : staging
     * status_name : 进行中
     */

    private List<GroupMissionsBean> group_missions;

    public int getStaging_award_amount() {
        return staging_award_amount;
    }

    public void setStaging_award_amount(int staging_award_amount) {
        this.staging_award_amount = staging_award_amount;
    }

    public int getStaging_award_count() {
        return staging_award_count;
    }

    public void setStaging_award_count(int staging_award_count) {
        this.staging_award_count = staging_award_count;
    }

    public List<PersonalMissionsBean> getPersonal_missions() {
        return personal_missions;
    }

    public void setPersonal_missions(List<PersonalMissionsBean> personal_missions) {
        this.personal_missions = personal_missions;
    }

    public List<GroupMissionsBean> getGroup_missions() {
        return group_missions;
    }

    public void setGroup_missions(List<GroupMissionsBean> group_missions) {
        this.group_missions = group_missions;
    }

    public static class PersonalMissionsBean {
        private int id;

        @Override
        public String toString() {
            return "PersonalMissionsBean{" +
                    "id=" + id +
                    ", mission=" + mission +
                    ", mama_id=" + mama_id +
                    ", year_week='" + year_week + '\'' +
                    ", finish_value=" + finish_value +
                    ", finish_time=" + finish_time +
                    ", status='" + status + '\'' +
                    ", status_name='" + status_name + '\'' +
                    '}';
        }

        /**
         * id : 1
         * name : 新增下属妈妈
         * kpi_type : count
         * target_value : 1
         * award_amount : 300
         */

        private MissionBean mission;
        private int mama_id;
        private String year_week;
        private int finish_value;
        private String finish_time;
        private String status;
        private String status_name;
        private int target_value;
        private double award_amount;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public MissionBean getMission() {
            return mission;
        }

        public void setMission(MissionBean mission) {
            this.mission = mission;
        }

        public int getMama_id() {
            return mama_id;
        }

        public void setMama_id(int mama_id) {
            this.mama_id = mama_id;
        }

        public String getYear_week() {
            return year_week;
        }

        public void setYear_week(String year_week) {
            this.year_week = year_week;
        }

        public int getFinish_value() {
            return finish_value;
        }

        public void setFinish_value(int finish_value) {
            this.finish_value = finish_value;
        }

        public String getFinish_time() {
            return finish_time;
        }

        public void setFinish_time(String finish_time) {
            this.finish_time = finish_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus_name() {
            return status_name;
        }

        public void setStatus_name(String status_name) {
            this.status_name = status_name;
        }

        public int getTarget_value() {
            return target_value;
        }

        public void setTarget_value(int target_value) {
            this.target_value = target_value;
        }

        public double getAward_amount() {
            return award_amount;
        }

        public void setAward_amount(double award_amount) {
            this.award_amount = award_amount;
        }

        public static class MissionBean {
            private int id;
            private String name;
            private String kpi_type;
            private int target_value;
            private double award_amount;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getKpi_type() {
                return kpi_type;
            }

            public void setKpi_type(String kpi_type) {
                this.kpi_type = kpi_type;
            }

            public int getTarget_value() {
                return target_value;
            }

            public void setTarget_value(int target_value) {
                this.target_value = target_value;
            }

            public double getAward_amount() {
                return award_amount;
            }

            public void setAward_amount(double award_amount) {
                this.award_amount = award_amount;
            }
        }
    }

    public static class GroupMissionsBean {
        private int id;

        @Override
        public String toString() {
            return "GroupMissionsBean{" +
                    "id=" + id +
                    ", mission=" + mission +
                    ", mama_id=" + mama_id +
                    ", year_week='" + year_week + '\'' +
                    ", finish_value=" + finish_value +
                    ", finish_time=" + finish_time +
                    ", status='" + status + '\'' +
                    ", status_name='" + status_name + '\'' +
                    '}';
        }

        /**
         * id : 2
         * name : 新增团队妈妈
         * kpi_type : count
         * target_value : 1
         * award_amount : 100
         */

        private MissionBean mission;
        private int mama_id;
        private String year_week;
        private int finish_value;
        private String finish_time;
        private String status;
        private String status_name;
        private int target_value;
        private double award_amount;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public MissionBean getMission() {
            return mission;
        }

        public void setMission(MissionBean mission) {
            this.mission = mission;
        }

        public int getMama_id() {
            return mama_id;
        }

        public void setMama_id(int mama_id) {
            this.mama_id = mama_id;
        }

        public String getYear_week() {
            return year_week;
        }

        public void setYear_week(String year_week) {
            this.year_week = year_week;
        }

        public int getFinish_value() {
            return finish_value;
        }

        public void setFinish_value(int finish_value) {
            this.finish_value = finish_value;
        }

        public String getFinish_time() {
            return finish_time;
        }

        public void setFinish_time(String finish_time) {
            this.finish_time = finish_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus_name() {
            return status_name;
        }

        public void setStatus_name(String status_name) {
            this.status_name = status_name;
        }

        public int getTarget_value() {
            return target_value;
        }

        public void setTarget_value(int target_value) {
            this.target_value = target_value;
        }

        public double getAward_amount() {
            return award_amount;
        }

        public void setAward_amount(double award_amount) {
            this.award_amount = award_amount;
        }

        public static class MissionBean {
            private int id;
            private String name;
            private String kpi_type;
            private int target_value;

            @Override
            public String toString() {
                return "MissionBean{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", kpi_type='" + kpi_type + '\'' +
                        ", target_value=" + target_value +
                        ", award_amount=" + award_amount +
                        '}';
            }

            private double award_amount;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getKpi_type() {
                return kpi_type;
            }

            public void setKpi_type(String kpi_type) {
                this.kpi_type = kpi_type;
            }

            public int getTarget_value() {
                return target_value;
            }

            public void setTarget_value(int target_value) {
                this.target_value = target_value;
            }

            public double getAward_amount() {
                return award_amount;
            }

            public void setAward_amount(double award_amount) {
                this.award_amount = award_amount;
            }
        }
    }
}
