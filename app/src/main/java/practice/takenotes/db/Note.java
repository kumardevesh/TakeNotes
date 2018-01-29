package practice.takenotes.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by shishir on 1/23/2018.
 */
@Entity(tableName = "note")
public class Note {
    @PrimaryKey(autoGenerate = true)
    long id;

    @ColumnInfo(name = "title")
    String title;

    @ColumnInfo(name = "image_url")
    String imageUrl = "";

    @ColumnInfo(name = "detail") String detail;

    @ColumnInfo(name = "created_on") String createdOn;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public boolean equals(Object obj) {
        Note note = (Note) obj;
        if(!imageUrl.equals(note.getImageUrl())) {
            return false;
        }
        if(!title.equals(note.getTitle())) {
            return false;
        }
        if(!(detail.equals(note.getDetail()))) {
            return false;
        }
        return true;
    }


}
