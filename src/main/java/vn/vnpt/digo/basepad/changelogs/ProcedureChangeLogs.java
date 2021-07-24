package vn.vnpt.digo.basepad.changelogs;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;
import com.mongodb.DB;

/**
 * Change logs
 *
 * @author thiennc
 */
@ChangeLog(order = "2020-07-14-18-43-00")
public class ProcedureChangeLogs {

    @ChangeSet(order = "2020-07-14-18-43-00", id = "ProcedureChangeLogs::create", author = "thiennc")
    public void create(DB db) {
        db.createCollection("form", null);

    }

    @ChangeSet(order = "2020-10-30-18-43-00", id = "ProcedureChangeLogs::index", author = "thiennc")
    public void createIndex(MongoDatabase db) {
        // create default objects
        Document formIndex = new Document();
        formIndex.put("name.name", 1);
        db.getCollection("form").createIndex(formIndex, new IndexOptions().unique(true).background(true));
    }
}
