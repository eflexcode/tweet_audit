package com.ifeanyi.storage.model;

import java.util.ArrayList;
import java.util.Date;

public class Tweet {

    public EditInfo edit_info;
    public boolean retweeted;
    public String source;
    public Entities entities;
    public ArrayList<String> display_text_range;
    public String favorite_count;
    public String id_str;
    public boolean truncated;
    public String retweet_count;
    public String id;
    public boolean possibly_sensitive;
    public String created_at;
    public boolean favorited;
    public String full_text;
    public String lang;
    public String in_reply_to_status_id_str;
    public String in_reply_to_user_id;
    public String in_reply_to_status_id;
    public String in_reply_to_screen_name;
    public String in_reply_to_user_id_str;
    public ExtendedEntities extended_entities;
    public boolean withheld_copyright;
    public ArrayList<String> withheld_in_countries;

    private class AdditionalMediaInfo{
        public boolean monetizable;
    }

    private class EditInfo{
        public Initial initial;
    }

    private class Entities{
        public ArrayList<Hashtag> hashtags;
        public ArrayList<Symbol> symbols;
        public ArrayList<UserMention> user_mentions;
        public ArrayList<Url> urls;
        public ArrayList<Medium> media;
    }

    private class ExtendedEntities{
        public ArrayList<Medium> media;
    }

    private class Hashtag{
        public String text;
        public ArrayList<String> indices;
    }

    private class Initial{
        public ArrayList<String> editTweetIds;
        public Date editableUntil;
        public String editsRemaining;
        public boolean isEditEligible;
    }

    private class Large{
        public String w;
        public String h;
        public String resize;
    }

    private class Medium{
        public String expanded_url;
        public ArrayList<String> indices;
        public String url;
        public String media_url;
        public String id_str;
        public String id;
        public String media_url_https;
        public Sizes sizes;
        public String type;
        public String display_url;
        public String source_status_id;
        public String source_user_id;
        public String source_user_id_str;
        public String source_status_id_str;
        public VideoInfo video_info;
        public AdditionalMediaInfo additional_media_info;
    }

    private class Medium2{
        public String w;
        public String h;
        public String resize;
    }

    private class Sizes{
        public Medium medium;
        public Small small;
        public Thumb thumb;
        public Large large;
    }

    private class Small{
        public String w;
        public String h;
        public String resize;
    }

    private class Symbol{
        public String text;
        public ArrayList<String> indices;
    }

    private class Thumb{
        public String w;
        public String h;
        public String resize;
    }



    private class Url{
        public String url;
        public String expanded_url;
        public String display_url;
        public ArrayList<String> indices;
    }

    private class UserMention{
        public String name;
        public String screen_name;
        public ArrayList<String> indices;
        public String id_str;
        public String id;
    }

    private class Variant{
        public String bitrate;
        public String content_type;
        public String url;
    }

    private class VideoInfo{
        public ArrayList<String> aspect_ratio;
        public String duration_millis;
        public ArrayList<Variant> variants;
    }



}
