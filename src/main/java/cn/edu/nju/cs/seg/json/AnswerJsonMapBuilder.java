package cn.edu.nju.cs.seg.json;

import cn.edu.nju.cs.seg.ServerConfig;
import cn.edu.nju.cs.seg.pojo.Answer;

import java.util.Map;

/**
 * Created by fwz on 2017/5/30.
 */
public class AnswerJsonMapBuilder implements JsonMapResponseBuilder {

    private Answer answer;

    public AnswerJsonMapBuilder(Answer answer) {
        this.answer = answer;
    }

    @Override
    public Map<String, Object> getSimpleMap() {
        Map<String, Object> map = new JsonMapBuilder()
                .append("id", answer.getId())
                .append("type", answer.getType() == Answer.TYPE_TEXT ? "text" : "audio")
                .append("answerer", answer.getAnswerer().getUsername())
                .append(answer.getType() == Answer.TYPE_TEXT ? "content" : "audio_url",
                        answer.getContent())
                .append("comments", answer.getCommentsNumber())
                .append("url", ServerConfig.SERVER_BASE_URL
                        + "/answers/" + answer.getId())
                .append("answerer_url", ServerConfig.SERVER_BASE_URL
                        + "/users/" + answer.getAnswerer().getId())
                .append("answerer_avatar_url", ServerConfig.SERVER_BASE_URL
                        + "/avatars/" + answer.getAnswerer().getAvatar())
                .append("comments_url", ServerConfig.SERVER_BASE_URL
                        + "/answers/" + answer.getId() + "/comments")
                .append("created_at", answer.getCreatedAt())
                .getMap();
        return map;
    }

    @Override
    public Map<String, Object> getComplexMap() {
        Map<String, Object> map = getSimpleMap();
        map.put("question_title", answer.getQuestion().getTitle());
        map.put("question_url", ServerConfig.SERVER_BASE_URL + "/questions/" + answer.getQuestion().getId());
        return map;
    }
}
