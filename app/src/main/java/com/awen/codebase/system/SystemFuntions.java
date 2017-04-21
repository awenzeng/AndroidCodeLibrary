package com.awen.codebase.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.Contacts.People;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;

public class SystemFuntions extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	//0在程序中驱动第三方应用
    Intent intent0=new Intent();  
    //-----核心部分----- 前名一个参数是应用程序的包名,后一个是这个应用程序的主Activity名  
    intent0.setComponent(new ComponentName("com.droidnova.android.games.vortex","com.droidnova.android.games.vortex..Vortex"));  
    startActivity(intent0);  
    
    
	//1.从google搜索内容 
	Intent intent = new Intent(); 
	intent.setAction(Intent.ACTION_WEB_SEARCH);
	intent.putExtra(SearchManager.QUERY,"searchString");
	startActivity(intent); 
	
	//2.浏览网页 
	Uri uri =Uri.parse("http://www.google.com"); 
	Intent it = new Intent(Intent.ACTION_VIEW,uri); 
	startActivity(it); 
	
	//3.显示地图 
	Uri uri2 = Uri.parse("geo:38.899533,-77.036476");
	Intent it1 = new Intent(Intent.ACTION_VIEW,uri); 
	startActivity(it1); 
	
	//4.路径规划 
	Uri uriPath =Uri.parse("http://maps.google.com/maps?f=dsaddr=startLat%20startLng&daddr=endLat%20endLng&hl=en");
	Intent it2 = new Intent(Intent.ACTION_VIEW,uriPath); 
	startActivity(it2); 
	
	//5.拨打电话 
	Uri uriPhone = Uri.parse("tel:xxxxxx"); 
	Intent it3 = new Intent(Intent.ACTION_DIAL,uri);
	startActivity(it3); 
	
	//6.调用发短信的程序 
	Intent it4 = new Intent(Intent.ACTION_VIEW);
	it4.putExtra("sms_body", "TheSMS text"); 
	it4.setType("vnd.android-dir/mms-sms");
	startActivity(it4);
	
	//7.发送短信 
	Uri uriMessage =Uri.parse("smsto:0800000123");
	Intent it5 = new Intent(Intent.ACTION_SENDTO, uri);
	it5.putExtra("sms_body", "TheSMS text");
	startActivity(it5); 
	
	//8.发送彩信 
	Uri uri6 =Uri.parse("content://media/external/images/media/23");
	Intent it6 = new Intent(Intent.ACTION_SEND);
	it6.putExtra("sms_body","some text");
	it6.putExtra(Intent.EXTRA_STREAM, uri);
	it6.setType("image/png");
	startActivity(it6); 
	
	//9.发送Email
	Uri uri7 =Uri.parse("mailto:xxx@abc.com"); 
	Intent it7 = new Intent(Intent.ACTION_SENDTO, uri); 
	startActivity(it7); 
	
	Intent it8 = new Intent(Intent.ACTION_SEND);
	it8.putExtra(Intent.EXTRA_EMAIL,"me@abc.com");
	it8.putExtra(Intent.EXTRA_TEXT, "Theemail body text");
	it8.setType("text/plain");
	startActivity(Intent.createChooser(it8,"Choose Email Client")); 
	
	Intent it9 =new Intent(Intent.ACTION_SEND);
	String[] tos={"me@abc.com"};
	String[]ccs={"you@abc.com"};
	it9.putExtra(Intent.EXTRA_EMAIL, tos);
	it9.putExtra(Intent.EXTRA_CC, ccs);
	it9.putExtra(Intent.EXTRA_TEXT, "Theemail body text");
	it9.putExtra(Intent.EXTRA_SUBJECT, "Theemail subject text");
	it9.setType("message/rfc822");
	startActivity(Intent.createChooser(it9,"Choose Email Client"));
	
	//10.发送附件：
	Intent it10 = new Intent(Intent.ACTION_SEND);
	it10.putExtra(Intent.EXTRA_SUBJECT, "Theemail subject text"); 
	it10.putExtra(Intent.EXTRA_STREAM,"file:///sdcard/mysong.mp3");
	startActivity(Intent.createChooser(it10,"Choose Email Client")); 
	
	//11.播放多媒体   
	Intent it11 = new Intent(Intent.ACTION_VIEW);
	Uri uri11 =Uri.parse("file:///sdcard/song.mp3"); 
	it11.setDataAndType(uri,"audio/mp3"); 
	startActivity(it11); 
	
	Uri uri12 =Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,"1");
	Intent it12 = new Intent(Intent.ACTION_VIEW,uri);
	startActivity(it12); 
	
	//13.卸载APK
	Uri uri13 =Uri.fromParts("package", "卸载包名", null);
	Intent it13 = new Intent(Intent.ACTION_DELETE, uri);
	startActivity(it13); 
	
	//14.安装APK
	Uri installUri = Uri.fromParts("package","安装报名", null); 
	Intent it14 = new Intent(Intent.ACTION_PACKAGE_ADDED, installUri); 
	startActivity(it14); 
	
	//15. 打开照相机 
	Intent i = new Intent(Intent.ACTION_CAMERA_BUTTON, null); 
    this.sendBroadcast(i); 
    
    // 16.从gallery选取图片 
    Intent i2 = new Intent(); 
    i2.setType("image/*"); 
    i2.setAction(Intent.ACTION_GET_CONTENT); 
    startActivityForResult(i2, 11); 
    
   // 17. 打开录音机 
    Intent mi = new Intent(Media.RECORD_SOUND_ACTION); 
    startActivity(mi); 
    
    
    //18.显示应用详细列表       
    Uri uri18 =Uri.parse("market://details?id=<包名>"); 
    Intent it18 = new Intent(Intent.ACTION_VIEW,uri);
    startActivity(it18);
    
  //  19.寻找应用       
    Uri uri19 =Uri.parse("market://search?q=pname:包名");
    Intent it19 = new Intent(Intent.ACTION_VIEW,uri);
    startActivity(it19); 
    
    //20.打开联系人列表
    Intent i3 = new Intent(); 
    i3.setAction(Intent.ACTION_GET_CONTENT); 
    i3.setType("vnd.android.cursor.item/phone"); 
    startActivityForResult(i3, 0); 
    
    Uri uri20 = Uri.parse("content://contacts/people"); 
    Intent it20 = new Intent(Intent.ACTION_PICK, uri); 
    startActivityForResult(it20, 0); 
    
   // 21. 打开另一程序 
    Intent i6 = new Intent(); 
    ComponentName cn = new ComponentName("com.yellowbook.android2", "com.yellowbook.android2.AndroidSearch"); 
    i.setComponent(cn); 
    i.setAction("android.intent.action.MAIN"); 
    startActivityForResult(i, RESULT_OK); 
    
   // 22.调用系统编辑添加联系人（高版本SDK有效）： 
    Intent it22 = new Intent(Intent.ACTION_INSERT_OR_EDIT); 
    it22.setType("vnd.android.cursor.item/contact"); 
    //it.setType(Contacts.CONTENT_ITEM_TYPE); 
    it22.putExtra("name","myName"); 
    it22.putExtra(android.provider.Contacts.Intents.Insert.COMPANY,"organization"); 
    it22.putExtra(android.provider.Contacts.Intents.Insert.EMAIL,"email"); 
    it22.putExtra(android.provider.Contacts.Intents.Insert.PHONE,"homePhone");
    it22.putExtra(android.provider.Contacts.Intents.Insert.SECONDARY_PHONE, "mobilePhone"); 
    it22.putExtra(android.provider.Contacts.Intents.Insert.TERTIARY_PHONE,"workPhone"); 
    it22.putExtra(android.provider.Contacts.Intents.Insert.JOB_TITLE,"title");
    startActivity(it22); 
    
   // 23.调用系统编辑添加联系人（全有效）： 
    Intent intent23 = new Intent(Intent.ACTION_INSERT_OR_EDIT); 
    intent.setType(People.CONTENT_ITEM_TYPE); 
    intent.putExtra(Contacts.Intents.Insert.NAME, "My Name"); 
    intent.putExtra(Contacts.Intents.Insert.PHONE, "+1234567890");
    intent.putExtra(Contacts.Intents.Insert.PHONE_TYPE,Contacts.PhonesColumns.TYPE_MOBILE); 
    intent.putExtra(Contacts.Intents.Insert.EMAIL, "com@com.com");
    intent.putExtra(Contacts.Intents.Insert.EMAIL_TYPE,Contacts.ContactMethodsColumns.TYPE_WORK);
    startActivity(intent); 
    }

    /**
     * 调用本地相机拍照并保存图片
     * 
     * @return
     */
    private String getPhoto() {
	
	String filepath = "/mnt/sdcard/DCIM/Camera/"+ System.currentTimeMillis() + ".png";
	final File file = new File(filepath);
	final Uri imageuri = Uri.fromFile(file);
	
	// TODO Auto-generated method stub
	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageuri);
	startActivityForResult(intent, 110);
	return (filepath);
    }

    /**
     * 调用本地相机录像并保存MP4文件
     * 
     * @return
     */
    private String getVideo() {
	
	String filepath = "/mnt/sdcard/DCIM/Camera/"+ System.currentTimeMillis() + ".MP4";
	final File file = new File(filepath);
	final Uri imageuri = Uri.fromFile(file);
	
	// TODO Auto-generated method stub
	Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
	intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageuri);
	startActivityForResult(intent, 110);

	return (filepath);
    }
    
    /**
     * 调用图库
     */
    private void getAlbum(){
	
	Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
	getImage.addCategory(Intent.CATEGORY_OPENABLE);
	getImage.setType("image/jpeg");
	startActivityForResult(getImage, 0);
    }
    /**
     * 调用拍照
     */
    private void getCamera(){
	Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
	startActivityForResult(getImageByCamera, 1);
    }
    
    /**
     * 从assets中读txt文件
     * @param context   
     * @param fileName  文件地址名：
     * @return
     */
    public String getAssetsString(Context context,String fileName){
		StringBuffer sb = new StringBuffer();
		try {
			AssetManager am = context.getAssets();
			InputStream in = am.open(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while((line = reader.readLine())!=null){
				line += ("\n");
				sb.append(line);
			}
			reader.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
    
    /**
     * 打开电筒
     */
    public void openFlashLight(){
    	Camera camera = Camera.open();	
		Parameters parameters = camera.getParameters();
		parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);//开启
		camera.setParameters(parameters);
    }
    /**
     * 关闭电筒
     */
    public void closeFlashLight(){
    	Camera camera = Camera.open();	
		Parameters parameters = camera.getParameters();
    	parameters.setFlashMode(Parameters.FLASH_MODE_OFF);//关闭
		camera.setParameters(parameters);
		camera.release();
    }
}
