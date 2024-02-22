package com.green.greengram4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFeedPicEntity is a Querydsl query type for FeedPicEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFeedPicEntity extends EntityPathBase<FeedPicEntity> {

    private static final long serialVersionUID = -1945173152L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFeedPicEntity feedPicEntity = new QFeedPicEntity("feedPicEntity");

    public final QCreatedAtEntity _super = new QCreatedAtEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QFeedEntity ifeed;

    public final NumberPath<Long> ifeedPics = createNumber("ifeedPics", Long.class);

    public final StringPath pic = createString("pic");

    public QFeedPicEntity(String variable) {
        this(FeedPicEntity.class, forVariable(variable), INITS);
    }

    public QFeedPicEntity(Path<? extends FeedPicEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFeedPicEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFeedPicEntity(PathMetadata metadata, PathInits inits) {
        this(FeedPicEntity.class, metadata, inits);
    }

    public QFeedPicEntity(Class<? extends FeedPicEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ifeed = inits.isInitialized("ifeed") ? new QFeedEntity(forProperty("ifeed"), inits.get("ifeed")) : null;
    }

}

