package apollo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageUtil {
	public static Bitmap getResBitmap(Context context, int resid) {
		Bitmap bmp = null;
		BitmapFactory.Options options = null;
		
		options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		bmp = BitmapFactory.decodeResource(context.getResources(), resid, options);
		return bmp;
	}
}
