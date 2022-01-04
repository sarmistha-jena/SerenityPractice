package utility;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Iterator;

public class MongoTest {
    public static void main(String[] args) {
        MongoClient mongoClient= new MongoClient("localhost", 27017);
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "myDb",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongoClient.getDatabase("myDb");
        System.out.println("Credentials ::"+ credential);

        database.createCollection("sampleCollection");
        System.out.println("Collection created successfully");

        // Retrieving a collection
        MongoCollection<Document> collection = database.getCollection("sampleCollection");
        System.out.println("Collection sampleCollection selected successfully");
        Document document = new Document("title", "MongoDB")
                .append("description", "database")
                .append("likes", 100)
                .append("url", "http://www.tutorialspoint.com/mongodb/")
                .append("by", "tutorials point");

        //Inserting document into the collection
        collection.insertOne(document);
        System.out.println("Document inserted successfully");
        // Retrieving the documents after updation
        // Getting the iterable object
        Bson filter = Filters.and(Filters.gt("qty", 3), Filters.lt("qty", 9));
        Bson filter1 = Filters.or(Filters.gt("qty", 3), Filters.lt("qty", 9));
        FindIterable<Document> iterDoc = collection.find(filter);
        int i = 1;
        // Getting the iterator
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) {

            System.out.println(it.next());
            i++;
        }
    }
}
