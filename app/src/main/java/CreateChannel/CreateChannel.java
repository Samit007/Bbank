package CreateChannel;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class CreateChannel {

    Context context;
    public final static String CHANNEL_1 = "Channel1";
    //public final static String CHANNEL_2 = "Channel2";

    public CreateChannel(Context context){
        this.context=context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1,
                    "Channel1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is channel 1");

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);

        }
    }


}
