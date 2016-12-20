package com.codecrafters.api.hypem;

import com.codecrafters.api.soundcloud.SoundcloudUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.util.Date;

/**
 * This class is used as mapping class for the song object on hypem.
 *
 * @author Fabian Dietenberger
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HypemSong {

    private String mediaid;
    private String artist;
    private String title;
    private Date dateposted;
    private String siteid;
    private String sitename;
    private String posturl;
    private String postid;
    private String time;
    private String description;
    private String fileUrl;

    @JsonProperty("loved_count")
    private int lovedCount;

    @JsonProperty("posted_count")
    private int postedCount;

    @JsonProperty("thumb_url")
    private String thumbUrl;

    @JsonProperty("thumb_url_medium")
    private String thumbUrlMedium;

    @JsonProperty("thumb_url_large")
    private String thumbUrlLarge;

    @JsonProperty("thumb_url_artist")
    private String thumbUrlArtist;

    public boolean isHostedOnSoundcloud() {
        return StringUtils.isNotBlank(fileUrl) &&
                SoundcloudUtils.isSoundcloudUrl(URI.create(fileUrl));
    }

    public String getMediaid() {
        return mediaid;
    }

    public void setMediaid(final String mediaid) {
        this.mediaid = mediaid;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(final String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Date getDateposted() {
        return dateposted;
    }

    public void setDateposted(final Date dateposted) {
        this.dateposted = dateposted;
    }

    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(final String siteid) {
        this.siteid = siteid;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(final String sitename) {
        this.sitename = sitename;
    }

    public String getPosturl() {
        return posturl;
    }

    public void setPosturl(final String posturl) {
        this.posturl = posturl;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(final String postid) {
        this.postid = postid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(final String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getLovedCount() {
        return lovedCount;
    }

    public void setLovedCount(final int lovedCount) {
        this.lovedCount = lovedCount;
    }

    public int getPostedCount() {
        return postedCount;
    }

    public void setPostedCount(final int postedCount) {
        this.postedCount = postedCount;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(final String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getThumbUrlMedium() {
        return thumbUrlMedium;
    }

    public void setThumbUrlMedium(final String thumbUrlMedium) {
        this.thumbUrlMedium = thumbUrlMedium;
    }

    public String getThumbUrlLarge() {
        return thumbUrlLarge;
    }

    public void setThumbUrlLarge(final String thumbUrlLarge) {
        this.thumbUrlLarge = thumbUrlLarge;
    }

    public String getThumbUrlArtist() {
        return thumbUrlArtist;
    }

    public void setThumbUrlArtist(final String thumbUrlArtist) {
        this.thumbUrlArtist = thumbUrlArtist;
    }

    @Override
    public String toString() {
        return "HypemSong{" +
                "mediaid='" + mediaid + '\'' +
                ", artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                ", dateposted=" + dateposted +
                ", siteid='" + siteid + '\'' +
                ", sitename='" + sitename + '\'' +
                ", posturl='" + posturl + '\'' +
                ", postid='" + postid + '\'' +
                ", time='" + time + '\'' +
                ", description='" + description + '\'' +
                ", lovedCount=" + lovedCount +
                ", postedCount=" + postedCount +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", thumbUrlMedium='" + thumbUrlMedium + '\'' +
                ", thumbUrlLarge='" + thumbUrlLarge + '\'' +
                ", thumbUrlArtist='" + thumbUrlArtist + '\'' +
                '}';
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(final String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
