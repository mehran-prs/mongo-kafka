/*
 * Copyright 2008-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Original Work: Apache License, Version 2.0, Copyright 2017 Hans-Peter Grahsl.
 */

package com.mongodb.kafka.connect.converter;

import java.util.Map;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.errors.DataException;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.BsonValueCodecProvider;
import org.bson.codecs.DocumentCodecProvider;
import org.bson.codecs.ValueCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

public class JsonSchemalessRecordConverter implements RecordConverter {

    private CodecRegistry codecRegistry =
            CodecRegistries.fromProviders(
                    new DocumentCodecProvider(),
                    new BsonValueCodecProvider(),
                    new ValueCodecProvider()
            ); // TODO get from MongoClientSettings

    @SuppressWarnings({"unchecked"})
    @Override
    public BsonDocument convert(final Schema schema, final Object value) {
        if (value == null) {
            throw new DataException("Error: value was null for JSON conversion");
        }
        return new Document((Map<String, Object>) value).toBsonDocument(null, codecRegistry);
    }
}