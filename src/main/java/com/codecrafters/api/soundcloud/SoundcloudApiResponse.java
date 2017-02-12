package com.codecrafters.api.soundcloud;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public class SoundcloudApiResponse {

    private String id;

    @JsonProperty("stream_url")
    private String streamUrl;

    @JsonProperty("durationInMillis")
    private int durationInMillis;

    @JsonProperty("waveform_url")
    private String waveformUrl;

    public SoundcloudApiResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(final String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public int getDurationInMillis() {
        return durationInMillis;
    }

    public void setDurationInMillis(final int durationInMillis) {
        this.durationInMillis = durationInMillis;
    }

    public String getWaveformUrl() {
        return waveformUrl;
    }

    public void setWaveformUrl(final String waveformUrl) {
        this.waveformUrl = waveformUrl;
    }

    @Override
    public String toString() {
        return "SoundcloudApiResponse{" +
                "id='" + id + '\'' +
                ", streamUrl='" + streamUrl + '\'' +
                ", durationInMillis=" + durationInMillis +
                ", waveformUrl='" + waveformUrl + '\'' +
                '}';
    }
}
