/*
 * Copyright 2019 InfAI (CC SES)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.jayway.jsonpath.ReadContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Rule {
    private String path;
    private Scope scope;
    private Comparision comparision;
    private String value;

    public Rule(String path, String scope, String comparision, Object value) {
        this.scope = new Scope(scope);
        this.path = path;
        this.comparision = new Comparision(comparision, value);
    }


    public static List<Rule> fromJson(JSONArray rules) {
        ArrayList<Rule> result = new ArrayList<Rule>();
        for (Object obj: rules) {
            result.add(Rule.fromJson((JSONObject)(obj)));
        }
        return result;
    }

    public static Rule fromJson(JSONObject jsonObj) {
        return new Rule(jsonObj.getString("path"),jsonObj.getString("scope"),jsonObj.getString("comparison"),jsonObj.get("value"));
    }

    public boolean check(ReadContext ctx){
        List<Object> pathResults = ctx.read(this.path);
        ArrayList<Boolean> resultset = new ArrayList<Boolean>();
        for(Object pathResult : pathResults){
            resultset.add(this.comparision.check(pathResult));
        }
        return this.scope.check(resultset);
    }
}
