//   Copyright 2012,2013 Vaughn Vernon
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package top.zhacker.boot.leveldb;

import org.iq80.leveldb.DB;
import org.springframework.beans.factory.annotation.Value;
import top.zhacker.core.exception.BusinessException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public abstract class AbstractLevelDBRepository {

    private DB database;
    
    @Value("${leveldb.path:/tmp/leveldb}")
    private String databasePath;
    

    @PostConstruct
    protected void init() {
        try {
            Files.createDirectories(Paths.get(databasePath));
        } catch (IOException e) {
            throw new BusinessException("level.db.path.invalid");
        }
        this.openDatabase(databasePath);
    }

    protected DB database() {
        return this.database;
    }

    protected String databasePath() {
        return this.databasePath;
    }

    private void setDatabase(DB aDatabase) {
        this.database = aDatabase;
    }

    private void setDatabasePath(String aDatabasePath) {
        this.databasePath = aDatabasePath;
    }

    private void openDatabase(String aDirectoryPath) {
        LevelDBProvider levelDBProvider = LevelDBProvider.instance();

        DB db = levelDBProvider.databaseFrom(aDirectoryPath);

        this.setDatabase(db);
        this.setDatabasePath(aDirectoryPath);
    }
}
