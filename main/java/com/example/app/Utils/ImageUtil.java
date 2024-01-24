package com.example.app.Utils;

import static java.lang.Boolean.FALSE;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.app.Game.GameConf;
import com.example.app.Piece.PieceImage;
import com.example.app.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/* 连连看的图片工具类 */
public class ImageUtil {
	private static List<Integer> imageValues = getImageValues();
	//private boolean Genshin = FALSE;//储存第一次点击的按钮以及是否第一次点击
	public static List<Integer> getImageValues() {
		try {
			// 得到R.drawable所有的属性, 即获取drawable目录下的所有图片
			Field[] drawableFields = R.drawable.class.getFields();
			List<Integer> resourceValues = new ArrayList<Integer>();
			for (Field field : drawableFields) {
				// 如果该Field的名称以p_开头
				if (field.getName().contains("m_p")) {
					resourceValues.add(field.getInt(R.drawable.class));
					//Log.i("image",field.getName());
				}
			}
			return resourceValues;
		} catch (Exception e) {
		    Log.i("image","not");
			return null;
		}
	}
	public static List<Integer> getRandomValues(List<Integer> sourceValues,
                                                int size) {
		// 创建一个随机数生成器
		Random random = new Random();
		// 创建结果集合
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			try {
				// 随机获取一个数字，大于、小于sourceValues.size()的数值
				int index = random.nextInt(sourceValues.size());
				// 从图片ID集合中获取该图片对象
				Integer image = sourceValues.get(index);
				// 添加到结果集中
				result.add(image);
			} catch (IndexOutOfBoundsException e) {
				return result;
			}
		}
		return result;
	}
	public static List<Integer> getPlayValues(int size) {
		if (size % 2 != 0) {
			// 如果该数除2有余数，将size加1
			size += 1;
		}
		// 再从所有的图片值中随机获取size的一半数量,即N/2张图片
		List<Integer> playImageValues = getRandomValues(imageValues, size / 2);
		// 将playImageValues集合的元素增加一倍（保证所有图片都有与之配对的图片），即N张图片
		playImageValues.addAll(playImageValues);
		// 将所有图片ID随机“洗牌”
		Collections.shuffle(playImageValues);
		return playImageValues;
	}
	public static List<PieceImage> getPlayImages(Context context, int size) {
		// 获取图片ID组成的集合
		List<Integer> resourceValues = getPlayValues(size);
		List<PieceImage> result = new ArrayList<PieceImage>();
		// 遍历每个图片ID
		for (Integer value : resourceValues) {
			// 加载图片
			Bitmap bm = BitmapFactory.decodeResource(context.getResources(),
					value);
            //Log.i("value",String.valueOf(bm.getWidth()));
			bm = Bitmap.createScaledBitmap(bm, GameConf.PIECE_WIDTH,GameConf.PIECE_HEIGHT,true);
			// 封装图片ID与图片本身
			PieceImage pieceImage = new PieceImage(bm, value);
			result.add(pieceImage);
		}
		return result;
	}
	public static Bitmap getSelectImage(Context context) {
		Bitmap bm = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.selected);

		bm = Bitmap.createScaledBitmap(bm,GameConf.PIECE_WIDTH,GameConf.PIECE_HEIGHT,true);

		return bm;
	}
}
