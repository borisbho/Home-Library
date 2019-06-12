package ho.boris.homelibrary;

import java.io.File;
import java.io.FileInputStream;

import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.GridFSUploadStream;

public class MongoInteractor {

	private MongoClient mongoClient;

	public MongoInteractor() {
		mongoClient = new MongoClient("localhost", 27017);
	}

	public static void main(String[] args) throws Exception {
		MongoInteractor interactor = new MongoInteractor();

		System.out.println("Getting Database");

		MongoDatabase db = interactor.getMongoClient().getDatabase("imageDB");

		GridFSBucket bucket = GridFSBuckets.create(db);

		GridFSUploadStream stream = bucket.openUploadStream("home1");
		byte[] imgBytes = interactor.getImage("home1.png");

		stream.write(imgBytes);

		ObjectId fildId = stream.getObjectId();
		stream.close();

		System.out.println("File Written Wth ID: " + fildId);

		System.out.println("Retrieving Photos");

		GridFSDownloadStream downloadStream = bucket.openDownloadStream("home1");
		int fileSize = (int) downloadStream.getGridFSFile().getLength();
		byte[] imageData = new byte[fileSize];
		downloadStream.read(imageData);
		System.out.println("Got Image Byte Array From Mongo");
		downloadStream.close();
	}

	public byte[] getImage(String imageId) {
		System.out.println("Retrieving Image from Mongo");
		MongoDatabase db = this.getMongoClient().getDatabase("ImageDB");
		GridFSBucket bucket = GridFSBuckets.create(db);
		GridFSDownloadStream downloadStream = bucket.openDownloadStream(imageId);
		int fileSize = (int) downloadStream.getGridFSFile().getLength();
		byte[] imageData = new byte[fileSize];
		downloadStream.read(imageData);
		downloadStream.close();
		System.out.println("GOT IMG BYTE ARRAY BACK");
		return imageData;
	}

	public byte[] LoadImageLocal(String filePath) throws Exception {
		File file = new File(filePath);
		int size = (int) file.length();
		byte[] data = new byte[size];
		FileInputStream in = new FileInputStream(file);
		in.read(data);
		in.close();
		return data;
	}

	public byte[] LoadImageFile(File file) throws Exception {
		int size = (int) file.length();
		byte[] buffer = new byte[size];
		FileInputStream in = new FileInputStream(file);
		in.read(buffer);
		in.close();
		return buffer;
	}

	public MongoClient getMongoClient() {
		return mongoClient;
	}

	public void setMongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;

	}

	public String addImageToMongo(File file, String streamId) throws Exception {
		MongoDatabase db = this.getMongoClient().getDatabase("imageDB");
		GridFSBucket bucket = GridFSBuckets.create(db);
		GridFSUploadStream stream = bucket.openUploadStream(streamId);

		byte[] imgBytes = this.LoadImageFile(file);
		stream.write(imgBytes);
		String imageId = stream.getObjectId().toString();
		stream.close();
		return imageId;
	}
}
