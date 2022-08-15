package com.codepath.apps.restclienttemplate.models;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TweetDao_Impl implements TweetDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfTweet;

  private final EntityInsertionAdapter __insertionAdapterOfUser;

  public TweetDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTweet = new EntityInsertionAdapter<Tweet>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `Tweet`(`id`,`body`,`createdAt`,`userId`,`medias`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Tweet value) {
        stmt.bindLong(1, value.id);
        if (value.body == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.body);
        }
        if (value.createdAt == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.createdAt);
        }
        stmt.bindLong(4, value.userId);
        final String _tmp;
        _tmp = Convert.fromArray(value.medias);
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, _tmp);
        }
      }
    };
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `User`(`id`,`name`,`screenName`,`profileImageUrl`,`tweetUrl`,`medias`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.id);
        if (value.name == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.name);
        }
        if (value.screenName == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.screenName);
        }
        if (value.profileImageUrl == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.profileImageUrl);
        }
        if (value.tweetUrl == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.tweetUrl);
        }
        final String _tmp;
        _tmp = Convert.fromArray(value.medias);
        if (_tmp == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp);
        }
      }
    };
  }

  @Override
  public void insertModel(final Tweet... tweets) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTweet.insert(tweets);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertModel(final User... users) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUser.insert(users);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<TweetWithUser> recentItems() {
    final String _sql = "SELECT Tweet.body AS tweet_body,Tweet.createdAt AS tweet_createdAt, Tweet.id AS tweet_id, User.*  FROM Tweet INNER JOIN User ON Tweet.userId = User.id ORDER BY Tweet.createdAt DESC LIMIT 5";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfBody = CursorUtil.getColumnIndexOrThrow(_cursor, "tweet_body");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "tweet_createdAt");
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "tweet_id");
      final int _cursorIndexOfId_1 = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfScreenName = CursorUtil.getColumnIndexOrThrow(_cursor, "screenName");
      final int _cursorIndexOfProfileImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "profileImageUrl");
      final int _cursorIndexOfTweetUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "tweetUrl");
      final int _cursorIndexOfMedias = CursorUtil.getColumnIndexOrThrow(_cursor, "medias");
      final List<TweetWithUser> _result = new ArrayList<TweetWithUser>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final TweetWithUser _item;
        final Tweet _tmpTweet;
        if (! (_cursor.isNull(_cursorIndexOfBody) && _cursor.isNull(_cursorIndexOfCreatedAt) && _cursor.isNull(_cursorIndexOfId))) {
          _tmpTweet = new Tweet();
          _tmpTweet.body = _cursor.getString(_cursorIndexOfBody);
          _tmpTweet.createdAt = _cursor.getString(_cursorIndexOfCreatedAt);
          _tmpTweet.id = _cursor.getLong(_cursorIndexOfId);
        }  else  {
          _tmpTweet = null;
        }
        final User _tmpUser;
        if (! (_cursor.isNull(_cursorIndexOfId_1) && _cursor.isNull(_cursorIndexOfName) && _cursor.isNull(_cursorIndexOfScreenName) && _cursor.isNull(_cursorIndexOfProfileImageUrl) && _cursor.isNull(_cursorIndexOfTweetUrl) && _cursor.isNull(_cursorIndexOfMedias))) {
          _tmpUser = new User();
          _tmpUser.id = _cursor.getLong(_cursorIndexOfId_1);
          _tmpUser.name = _cursor.getString(_cursorIndexOfName);
          _tmpUser.screenName = _cursor.getString(_cursorIndexOfScreenName);
          _tmpUser.profileImageUrl = _cursor.getString(_cursorIndexOfProfileImageUrl);
          _tmpUser.tweetUrl = _cursor.getString(_cursorIndexOfTweetUrl);
          final String _tmp;
          _tmp = _cursor.getString(_cursorIndexOfMedias);
          _tmpUser.medias = Convert.fromString(_tmp);
        }  else  {
          _tmpUser = null;
        }
        _item = new TweetWithUser();
        _item.tweet = _tmpTweet;
        _item.user = _tmpUser;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
