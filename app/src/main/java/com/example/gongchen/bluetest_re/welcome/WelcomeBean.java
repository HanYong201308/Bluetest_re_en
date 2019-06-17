package com.example.gongchen.bluetest_re.welcome;

import java.util.List;

class WelcomeBean {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 4
         * name : null
         * idx : 6
         * photourl : http://192.168.2.110:8081/loop/image/NW1lDfAuQX.png
         * createtime : null
         * updatetime : null
         */

        private int id;
        private Object name;
        private int idx;
        private String photourl;
        private Object createtime;
        private Object updatetime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public int getIdx() {
            return idx;
        }

        public void setIdx(int idx) {
            this.idx = idx;
        }

        public String getPhotourl() {
            return photourl;
        }

        public void setPhotourl(String photourl) {
            this.photourl = photourl;
        }

        public Object getCreatetime() {
            return createtime;
        }

        public void setCreatetime(Object createtime) {
            this.createtime = createtime;
        }

        public Object getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(Object updatetime) {
            this.updatetime = updatetime;
        }
    }
}
