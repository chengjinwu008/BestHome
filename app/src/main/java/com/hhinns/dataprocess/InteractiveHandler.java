package com.hhinns.dataprocess;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.hhinns.library.CommonUtils;

import android.content.Context;


public class InteractiveHandler {
	public static final int CLIENT_PROTOCOL_ERROR = -1;
	public static final int IO_EXCEPTION = -2;
	public static final int OTHER_EXCEPTION = -3;
	public static final int UNKOWN_HOST_EXCEPTION = -4;
	public static final int SOCKET_TIMEOUT_EXCEPTION = -5;
	public static final int CONNECTION_TIMEOUT_EXCEPTION = -6;
	public static final int DISCONNECTION_FLAG = -8;
	public static final int SERVER_TIMEOUT = 5000;
	public static final String CHARSET = "UTF-8";
	public static final String SERVER_ADDRESS = "http://192.168.10.41/xalv/client/";
	public static final String RESULT_FORMAT = "<channel runtime=\"00.1718750\" allcount=\"1\" singlepage=\"10\" nowpage=\"1\" titlekey=\"\" limit=\"\"><item><systemMessage>%s</systemMessage></item></channel>";

	private static final String TAG = "cn.jewleo.interactive.InteractiveHandler";
	public static boolean DOWNLOAD = true;
	private static final String API_URL = "/app/update/index/%s/android/v2/%s/";
	public static final String SERVER_IMG_ADDRESS = "http://www.zgjdyy.cn";
	private Context mContext = null;
	private int mRepeatConnectTimes = 3;

	public InteractiveHandler(Context context) {
		mContext = context;

	}

//	public File downloadZip(String urlStr, UpdateProgressCallback callback)
//			throws Exception {
//		HttpURLConnection conn = null;
//		File file = null;
//		BufferedInputStream inStream = null;
//		BufferedOutputStream outStream = null;
//		String fileName = null;
//		long totalSize = 0;
//		int length = 0;
//		int count = 0;
//		byte[] buffer = null;
//		try {
//			if (CommonUtils.sdcardIsAvailable()) {
//
//				if (null != urlStr && !"".equals(urlStr)) {
//					fileName = urlStr.substring(urlStr.lastIndexOf("/") + 1);
//					if (null != fileName && fileName.contains(".db")) {
//
//						file = FileUtils.createDirectory(mContext,
//								FileHandler.DIRECTORY);
//						if (null != file && file.isDirectory()) {
//							file = new File(file, fileName);
//							if (null != file) {
//								if (file.exists()) {
//									file.delete();
//								}
//
//								conn = establishConnection(urlStr);
//
//								if (null != conn
//										&& conn.getResponseCode() == 200) {
//									totalSize = conn.getContentLength();
//									if (totalSize > 0) {
//										buffer = new byte[1024];
//										inStream = new BufferedInputStream(
//												conn.getInputStream());
//										outStream = new BufferedOutputStream(
//												new FileOutputStream(file));
//										while ((length = inStream.read(buffer)) != -1) {
//											outStream.write(buffer, 0, length);
//											count += length;
//											callback.updateProgress(count,
//													totalSize);
//										}
//										outStream.flush();
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//			return file;
//		} finally {
//			callback = null;
//			urlStr = null;
//			file = null;
//			buffer = null;
//			try {
//				if (inStream != null) {
//					inStream.close();
//					inStream = null;
//				}
//				if (outStream != null) {
//					outStream.close();
//					outStream = null;
//				}
//			} catch (Exception ex) {
//				System.out.println(String.format("%s:%s---->%s", TAG,
//						"downloadZip:Stream", ex.getMessage()));
//			}
//			if (conn != null) {
//				conn.disconnect();
//				conn = null;
//			}
//			fileName = null;
//		}
//	}
	
	public JSONObject httpGetRequest(String actionUrl)
			throws Exception
	{
		JSONObject result = null;

		HttpParams httpParams = null;
		HttpPost get = null;
		DefaultHttpClient client = null;
		HttpResponse response = null;
		String mUrl = null;

		try
		{
			if (CommonUtils.networkIsAvaiable(mContext))
			{
				mUrl =  actionUrl;

				get = new HttpPost(mUrl);
				System.out.println("---------------->" + mUrl);
				httpParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParams,
						SERVER_TIMEOUT);
				HttpConnectionParams.setSoTimeout(httpParams, SERVER_TIMEOUT);

				client = new DefaultHttpClient(httpParams);
				client.addRequestInterceptor(new HttpRequestInterceptor()
				{
					@Override
					public void process(HttpRequest request, HttpContext context)
							throws HttpException, IOException
					{
						if (!request.containsHeader("Content-Type"))
						{
							request.addHeader("Content-Type", "text/xml");
						} else
						{
							request.setHeader("Content-Type", "text/xml");
						}
						if (!request.containsHeader("Accept-Encoding"))
						{
							request.addHeader("Accept-Encoding", "gzip");
						}
					}
				});
				client.addResponseInterceptor(new HttpResponseInterceptor()
				{
					@Override
					public void process(HttpResponse response,
							HttpContext context) throws HttpException,
							IOException
					{
						HttpEntity entity = null;
						Header contentEncoding = null;
						HeaderElement[] codecs = null;
						try
						{
							entity = response.getEntity();
							contentEncoding = entity.getContentEncoding();
							if (null != contentEncoding)
							{
								codecs = contentEncoding.getElements();
								for (HeaderElement element : codecs)
								{
									if (element.getName().equalsIgnoreCase(
											"gzip"))
									{
										response.setEntity(new GzipDecompressingEntity(
												entity));
										break;
									}
								}
							}
						} finally
						{
							if (null != entity)
							{
								entity = null;
							}
							if (null != contentEncoding)
							{
								contentEncoding = null;
							}
							if (null != codecs)
							{
								codecs = null;
							}
						}
					}
				});

				response = client.execute(get);
				if (response.getStatusLine().getStatusCode() == 200)
				{
					result = new JSONObject(response.getEntity().toString());
				}
			} else
			{
				result =  new JSONObject(String.format(RESULT_FORMAT, DISCONNECTION_FLAG));
			}

			return result;
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println();
		}finally
		{
			
			actionUrl = null;

			result = null;
			httpParams = null;
			if (get != null && !get.isAborted())
			{
				get.abort();
			}
			get = null;
			if (client != null)
			{
				client.getConnectionManager().shutdown();
			}
			client = null;
			mUrl = null;
			response = null;
		}
		return result;
	}
	
	private byte[] toData(String encoding, InputStream is) throws IOException{
		
		boolean gzip = "gzip".equalsIgnoreCase(encoding);
		
		if(gzip){
			is = new GZIPInputStream(is);
		}
		
		return AQUtility.toBytes(is);
	}

//	public String httpGetRequest(String param) throws Exception {
//		String result = "";
//		HttpGet httpGet = null;
//		HttpParams httpParams = null;
//		DefaultHttpClient client = null;
//		HttpResponse response = null;
//		try {
//			if (CommonUtils.networkIsAvaiable(mContext)) 
//			{
//				
//				
//				httpGet = new HttpGet(String.format("%s%s",
//						mContext.getString(R.string.base_address),
//						param));
//
//				httpParams = new BasicHttpParams();
//				HttpConnectionParams.setConnectionTimeout(httpParams,
//						SERVER_TIMEOUT);
//				HttpConnectionParams.setSoTimeout(httpParams, SERVER_TIMEOUT);
//
//				client = new DefaultHttpClient(httpParams);
//				client.addRequestInterceptor(new HttpRequestInterceptor() {
//					@Override
//					public void process(HttpRequest request, HttpContext context)
//							throws HttpException, IOException {
//						if (!request.containsHeader("Content-Type")) {
//							request.addHeader("Content-Type", "text/xml");
//						} else {
//							request.setHeader("Content-Type", "text/xml");
//						}
//						if (!request.containsHeader("Accept-Encoding")) {
//							request.addHeader("Accept-Encoding", "gzip");
//						}
//					}
//				});
//				client.addResponseInterceptor(new HttpResponseInterceptor() {
//					@Override
//					public void process(HttpResponse response,
//							HttpContext context) throws HttpException,
//							IOException {
//						HttpEntity entity = null;
//						Header contentEncoding = null;
//						HeaderElement[] codecs = null;
//						try {
//							entity = response.getEntity();
//							contentEncoding = entity.getContentEncoding();
//							if (null != contentEncoding) {
//								codecs = contentEncoding.getElements();
//								for (HeaderElement element : codecs) {
//									if (element.getName().equalsIgnoreCase(
//											"gzip")) {
//										response.setEntity(new GzipDecompressingEntity(
//												entity));
//										break;
//									}
//								}
//							}
//						} finally {
//							if (null != entity) {
//								entity = null;
//							}
//							if (null != contentEncoding) {
//								contentEncoding = null;
//							}
//							if (null != codecs) {
//								codecs = null;
//							}
//						}
//					}
//				});
//
//				response = client.execute(httpGet);
//				if (response.getStatusLine().getStatusCode() == 200) {
//					result = EntityUtils
//							.toString(response.getEntity(), CHARSET);
//				}
//			} else {
//				result = String.format(RESULT_FORMAT, DISCONNECTION_FLAG);
//			}
//
//			return result;
//		} finally {
//			param = null;
//			result = null;
//
//			if (httpGet != null && !httpGet.isAborted()) {
//				httpGet.abort();
//			}
//			httpGet = null;
//
//			httpParams = null;
//
//			if (client != null) {
//				client.getConnectionManager().shutdown();
//			}
//			client = null;
//
//			response = null;
//		}
//	}

	public String httpPostRequest(HashMap<String, String> map,
			String actionUrl) throws Exception {
		String result = "";

		HttpParams httpParams = null;
		HttpPost post = null;
		StringEntity entityData = null;
		DefaultHttpClient client = null;
		HttpResponse response = null;
		String mUrl = null;

		try {
			if (CommonUtils.networkIsAvaiable(mContext)) {
				mUrl = String.format("%s?%s", SERVER_ADDRESS, actionUrl);

				post = new HttpPost(mUrl);
				entityData = new StringEntity(parseXMLStr(map), CHARSET);
				post.setEntity(entityData);

				httpParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParams,
						SERVER_TIMEOUT);
				HttpConnectionParams.setSoTimeout(httpParams, SERVER_TIMEOUT);

				client = new DefaultHttpClient(httpParams);
				client.addRequestInterceptor(new HttpRequestInterceptor() {
					@Override
					public void process(HttpRequest request, HttpContext context)
							throws HttpException, IOException {
						if (!request.containsHeader("Content-Type")) {
							request.addHeader("Content-Type", "text/xml");
						} else {
							request.setHeader("Content-Type", "text/xml");
						}
						if (!request.containsHeader("Accept-Encoding")) {
							request.addHeader("Accept-Encoding", "gzip");
						}
					}
				});
				client.addResponseInterceptor(new HttpResponseInterceptor() {
					@Override
					public void process(HttpResponse response,
							HttpContext context) throws HttpException,
							IOException {
						HttpEntity entity = null;
						Header contentEncoding = null;
						HeaderElement[] codecs = null;
						try {
							entity = response.getEntity();
							contentEncoding = entity.getContentEncoding();
							if (null != contentEncoding) {
								codecs = contentEncoding.getElements();
								for (HeaderElement element : codecs) {
									if (element.getName().equalsIgnoreCase(
											"gzip")) {
										response.setEntity(new GzipDecompressingEntity(
												entity));
										break;
									}
								}
							}
						} finally {
							if (null != entity) {
								entity = null;
							}
							if (null != contentEncoding) {
								contentEncoding = null;
							}
							if (null != codecs) {
								codecs = null;
							}
						}
					}
				});

				response = client.execute(post);
				if (response.getStatusLine().getStatusCode() == 200) {
					result = EntityUtils
							.toString(response.getEntity(), CHARSET);
				}
			} else {
				result = String.format(RESULT_FORMAT, DISCONNECTION_FLAG);
			}

			return result;
		} finally {
			map = null;
			
			actionUrl = null;

			result = null;
			httpParams = null;
			if (post != null && !post.isAborted()) {
				post.abort();
			}
			post = null;
			entityData = null;
			if (client != null) {
				client.getConnectionManager().shutdown();
			}
			client = null;
			mUrl = null;
			response = null;
		}
	}

	private String parseXMLStr(HashMap<String, String> map) {
		StringBuilder sb = null;
		try {
			sb = new StringBuilder("");
			if (map != null && !map.isEmpty()) {
				sb.append("<item>");
				for (Map.Entry<String, String> entry : map.entrySet()) {
					sb.append(String.format("<%s>%s</%s>", entry.getKey(),
							entry.getValue(), entry.getKey()));
				}
				sb.append("</item>");
			}
			return sb.toString();
		} catch (Exception ex) {
			return null;
		} finally {
			sb = null;
		}
	}

	private static class GzipDecompressingEntity extends HttpEntityWrapper {

		public GzipDecompressingEntity(final HttpEntity entity) {
			super(entity);
		}

		@Override
		public InputStream getContent() throws IOException,
				IllegalStateException {
			InputStream wrappedin = wrappedEntity.getContent();
			return new GZIPInputStream(wrappedin);
		}

		@Override
		public long getContentLength() {
			return -1;
		}
	}

	private HttpURLConnection establishConnection(String urlStr) {
		URL url = null;
		HttpURLConnection conn = null;
		try {
			url = new URL(urlStr);

			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(3000);

			conn.getResponseCode();
			return conn;
		} catch (Exception ex) {
			if (ex.getClass().equals(java.net.ConnectException.class)
					|| ex.getClass().equals(
							java.net.SocketTimeoutException.class)
					|| ex.getClass()
							.equals(java.net.UnknownHostException.class)) {
				if (mRepeatConnectTimes >= 0) {
					if (ex.getClass().equals(
							java.net.UnknownHostException.class)) {
						try {
							Thread.sleep(SERVER_TIMEOUT);
						} catch (InterruptedException e) {
						}
					}

					mRepeatConnectTimes--;
					conn = establishConnection(urlStr);
				}
			}
			return conn;
		} finally {
			urlStr = null;
			url = null;
			conn = null;
		}
	}
}
