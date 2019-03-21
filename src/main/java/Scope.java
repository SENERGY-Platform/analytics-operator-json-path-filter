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


import java.util.List;

public class Scope {
    private String scope;
    private int modifier;

    public Scope(String scope) {
        if (scope.startsWith("max")){
            this.scope = "max";
            this.modifier = Integer.parseInt(scope.substring(3));
        }else if(scope.startsWith("min")){
            this.scope = "min";
            this.modifier = Integer.parseInt(scope.substring(3));
        }else if(scope.matches("\\d+")){
            this.scope = "exact";
            this.modifier = Integer.parseInt(scope);
        }else{
            this.scope = scope;
        }
    }

    public boolean check(List<Boolean> resultset) {
        switch (this.scope){
            case "any":
                return this.any(resultset);
            case "all":
                return this.all(resultset);
            case "none":
                return this.none(resultset);
            case "min":
                return this.min(resultset, this.modifier);
            case "max":
                return this.max(resultset, this.modifier);
            case "exact":
                return this.exact(resultset, this.modifier);
        }
        return false;
    }

    private boolean any(List<Boolean> resultset) {
        for(boolean b : resultset){
            if (b) {
                return true;
            }
        }
        return false;
    }

    private boolean all(List<Boolean> resultset) {
        for(boolean b : resultset){
            if (!b) {
                return false;
            }
        }
        return true;
    }

    private boolean none(List<Boolean> resultset) {
        for(boolean b : resultset){
            if (b) {
                return false;
            }
        }
        return true;
    }

    private boolean min(List<Boolean> resultset, int min) {
        int count = 0;
        for(boolean b : resultset){
            if (b) {
                count++;
                if (count >= min) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean max(List<Boolean> resultset, int max) {
        int count = 0;
        for(boolean b : resultset){
            if (b) {
                count++;
                if (count > max) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean exact(List<Boolean> resultset, int exact) {
        int count = 0;
        for(boolean b : resultset){
            if (b) {
                count++;
                if (count > exact) {
                    return false;
                }
            }
        }
        return exact == count;
    }
}
