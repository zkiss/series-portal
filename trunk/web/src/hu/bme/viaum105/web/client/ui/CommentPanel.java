package hu.bme.viaum105.web.client.ui;

import hu.bme.viaum105.web.shared.dto.persistent.CommentDto;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CommentPanel extends VerticalPanel {
	
	CommentDto comment;
	
	Label userName = new Label();
	Label commentText = new Label();
	
	public CommentPanel() {
		add(userName);
		add(commentText);
		
		userName.addStyleName("commentHeader");
	}
	
	public void showComment(CommentDto comment) {
		this.comment = comment;
		userName.setText(comment.getUser().getLoginName()+" on "+comment.getDate());
		commentText.setText(comment.getComment());
	}
	
	public CommentDto getComment() {
		return comment;
	}

}
