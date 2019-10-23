package com.dante.diary.custom;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;

import com.dante.diary.R;
import com.dante.diary.chat.ConversationActivity;
import com.dante.diary.utils.SpUtil;

import java.util.LinkedList;
import java.util.List;

import androidx.core.app.NotificationCompat;

import static com.dante.diary.base.App.context;

/**
 * Created by wli on 15/8/26.
 */
public class NotificationUtils {

    private static final String CHANNEL_ID = "CHANNEL_ID";
    /**
     * tag list，用来标记是否应该展示 Notification
     * 比如已经在聊天页面了，实际就不应该再弹出 notification
     */
    private static List<String> notificationTagList = new LinkedList<String>();

    /**
     * 添加 tag 到 tag list，在 MessageHandler 弹出 notification 前会判断是否与此 tag 相等
     * 若相等，则不弹，反之，则弹出
     *
     * @param tag
     */
    public static void addTag(String tag) {
        if (!notificationTagList.contains(tag)) {
            notificationTagList.add(tag);
        }
    }

    public static void removeTag(String tag) {
        notificationTagList.remove(tag);
    }

    /**
     * 判断是否应该弹出 notification
     * 判断标准是该 tag 是否包含在 tag list 中
     */
    public static boolean isShowNotification(String tag) {
        return !notificationTagList.contains(tag);
    }

    public static void showNotification(int mId, String title, String content, Intent intent) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setAutoCancel(true)
                        .setContentTitle(title)
                        .setContentText(content);

        if (SpUtil.getBoolean("notifications_new_message", true)) {
            if (SpUtil.getBoolean("notifications_new_message_vibrate")) {
                mBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
            } else {
                mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            }
        }

        TaskStackBuilder builder = TaskStackBuilder.create(context)
                .addParentStack(ConversationActivity.class)
                .addNextIntent(intent);

        mBuilder.setContentIntent(builder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT));
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        if (mNotificationManager != null) {
            mNotificationManager.notify(mId, mBuilder.build());
        }
    }
}
