package com.afims.service;
import com.afims.entity.Feedback;
import org.springframework.stereotype.Service;
import java.util.*;
@Service public class FeedbackAnalysisService {
    public Map<String,Object> analyze(Feedback f) {
        String text=((f.getComment()==null?"":f.getComment())+" "+(f.getSuggestion()==null?"":f.getSuggestion())).toLowerCase();
        List<String> improve=new ArrayList<>(),positive=new ArrayList<>();
        if(text.contains("practical")||text.contains("example")) improve.add("Practical examples");
        if(text.contains("doubt")||text.contains("clarif")) improve.add("Doubt clarification sessions");
        if(text.contains("communication")) improve.add("Communication");
        if(f.getOverallRating()>=4) positive.add("Strong overall learning experience");
        if(text.contains("clear")) positive.add("Teaching clarity");
        String sentiment=f.getOverallRating()>=4?"Positive":f.getOverallRating()<=2?"Needs attention":"Mixed";
        String priority=improve.size()>=2||f.getOverallRating()<=2?"High":improve.size()==1?"Medium":"Low";
        String rec=improve.isEmpty()?"Continue the current learning practices and monitor future feedback.":"Add practical demonstrations and structured doubt-clarification opportunities.";
        return Map.of("sentiment",sentiment,"improvementThemes",improve,"positiveThemes",positive,"priority",priority,"recommendation",rec);
    }
}
