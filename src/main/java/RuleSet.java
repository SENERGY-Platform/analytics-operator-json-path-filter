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


import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.ReadContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RuleSet {
    private Scope scope;
    private List<Rule> rules;

    public RuleSet(String jsonString) throws JSONException {
        JSONObject jsonObj = new JSONObject(jsonString);
        this.scope = new Scope(jsonObj.getString("scope"));
        this.rules = Rule.fromJson(jsonObj.getJSONArray("rules"));
    }

    public boolean check(String msg) {
        ReadContext ctx = JsonPath.using(
                Configuration.defaultConfiguration().addOptions(
                        Option.ALWAYS_RETURN_LIST,
                        Option.SUPPRESS_EXCEPTIONS
                )).parse(msg);
        ArrayList<Boolean> resultset = new ArrayList<Boolean>();
        for(Rule rule : this.rules){
            resultset.add(rule.check(ctx));
        }
        return this.scope.check(resultset);
    }
}
