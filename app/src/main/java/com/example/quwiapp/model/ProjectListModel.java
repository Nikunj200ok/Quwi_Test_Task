package com.example.quwiapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectListModel {
    @SerializedName("projects")
    @Expose
    private List<Project> projects = null;

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public class Project {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("uid")
        @Expose
        private String uid;
        @SerializedName("logo_url")
        @Expose
        private Object logoUrl;
        @SerializedName("position")
        @Expose
        private int position;
        @SerializedName("is_active")
        @Expose
        private int isActive;
        @SerializedName("is_owner_watcher")
        @Expose
        private boolean isOwnerWatcher;
        @SerializedName("dta_user_since")
        @Expose
        private Object dtaUserSince;
        @SerializedName("users")
        @Expose
        private List<User> users = null;

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

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public Object getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(Object logoUrl) {
            this.logoUrl = logoUrl;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getIsActive() {
            return isActive;
        }

        public void setIsActive(int isActive) {
            this.isActive = isActive;
        }

        public boolean isIsOwnerWatcher() {
            return isOwnerWatcher;
        }

        public void setIsOwnerWatcher(boolean isOwnerWatcher) {
            this.isOwnerWatcher = isOwnerWatcher;
        }

        public Object getDtaUserSince() {
            return dtaUserSince;
        }

        public void setDtaUserSince(Object dtaUserSince) {
            this.dtaUserSince = dtaUserSince;
        }

        public List<User> getUsers() {
            return users;
        }

        public void setUsers(List<User> users) {
            this.users = users;
        }

    }

    public class User implements Parcelable {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("avatar_url")
        @Expose
        private Object avatarUrl;
        @SerializedName("is_online")
        @Expose
        private boolean isOnline;
        @SerializedName("dta_activity")
        @Expose
        private String dtaActivity;
        @SerializedName("dta_since")
        @Expose
        private Object dtaSince;

        protected User(Parcel in) {
            id = in.readInt();
            name = in.readString();
            isOnline = in.readByte() != 0;
            dtaActivity = in.readString();
        }

        public final Creator<User> CREATOR = new Creator<User>() {
            @Override
            public User createFromParcel(Parcel in) {
                return new User(in);
            }

            @Override
            public User[] newArray(int size) {
                return new User[size];
            }
        };

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

        public Object getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(Object avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public boolean isIsOnline() {
            return isOnline;
        }

        public void setIsOnline(boolean isOnline) {
            this.isOnline = isOnline;
        }

        public String getDtaActivity() {
            return dtaActivity;
        }

        public void setDtaActivity(String dtaActivity) {
            this.dtaActivity = dtaActivity;
        }

        public Object getDtaSince() {
            return dtaSince;
        }

        public void setDtaSince(Object dtaSince) {
            this.dtaSince = dtaSince;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(name);
            dest.writeByte((byte) (isOnline ? 1 : 0));
            dest.writeString(dtaActivity);
        }
    }
}
