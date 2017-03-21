
package com.dante.diary.detail;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dante.diary.R;
import com.dante.diary.interfaces.OnItemClickListener;
import com.dante.diary.model.Comment;
import com.dante.diary.utils.TimeUtil;
import com.jaychang.st.SimpleText;

import java.util.List;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.dante.diary.base.App.context;

/**
 * Created by yons on 17/3/10.
 */

public class DiaryCommentsAdapter extends BaseQuickAdapter<Comment, BaseViewHolder> {

    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.content)
    TextView content;
    private OnItemClickListener listener;

    public DiaryCommentsAdapter(List<Comment> data) {
        super(R.layout.list_comment_item, data);
    }

    public DiaryCommentsAdapter(List<Comment> data, OnItemClickListener l) {
        super(R.layout.list_comment_item, data);
        listener = l;
    }

    @Override
    protected void convert(BaseViewHolder helper, Comment item) {
        helper.addOnClickListener(R.id.commentAvatar)
                .addOnClickListener(R.id.commentName)
                .addOnClickListener(R.id.commentContent);

        TextView commentContent = helper.getView(R.id.commentContent);
        String content = item.getContent();
        if (item.getRecipient() == null) {
            commentContent.setText(content);
        } else {
            //评论内容前面加上"回复 接收人："
            String recipient = item.getRecipient().getName();
            content = String.format("回复 %s ：" + content, recipient);

            SimpleText sText = SimpleText.create(helper.itemView.getContext(), content)
                    .first(recipient)
                    .pressedTextColor(R.color.btg_global_text_blue)
                    .onClick((charSequence, range, o) -> {
                        if (listener != null) {
                            listener.onItemClick(helper.getAdapterPosition());
                        }
                    });
            sText.linkify(commentContent);
            commentContent.setText(sText);
        }

        helper.setText(R.id.commentName, item.getUser().getName())
                .setText(R.id.commentTime, TimeUtil.getTimeText(item.getCreated()));

        //评论用户的头像
        Glide.with(helper.itemView.getContext())
                .load(item.getUser().getAvatarUrl())
                .bitmapTransform(new CropCircleTransformation(context))
                .into((ImageView) helper.getView(R.id.commentAvatar));
    }
}
