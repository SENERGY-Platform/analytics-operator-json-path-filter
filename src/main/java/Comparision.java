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


public class Comparision {
    private String comp;
    private Object value;

    public Comparision(String comparision, Object value) {
        this.comp = comparision;
        this.value = value;
    }

    public boolean check(Object pathResult) {
        switch (this.comp) {
            case "==":
                return this.compEqual(pathResult);
            case "!=":
                return this.compUnequal(pathResult);
            case ">=":
                return this.compBiggerOrEqual(pathResult);
            case "<=":
                return this.compSmalerOrEqual(pathResult);
            case ">":
                return this.compBigger(pathResult);
            case "<":
                return this.compSmaler(pathResult);
            case "regex":
                return this.compRegexMatch(pathResult);
        }
        return false;
    }

    private boolean compEqual(Object pathResult) {
        return pathResult.equals(this.value);
    }

    private boolean compUnequal(Object pathResult) {
        return !pathResult.equals(this.value);
    }

    private boolean compBiggerOrEqual(Object pathResult) {
        if (this.value instanceof Integer){
            if (pathResult instanceof Integer) {
                return (Integer)this.value >= (Integer)pathResult;
            }else if(pathResult instanceof Double){
                return (Integer)this.value >= (Double)pathResult;
            }
        }else if (this.value instanceof Double){
            if (pathResult instanceof Integer) {
                return (Double)this.value >= (Integer)pathResult;
            }else if(pathResult instanceof Double){
                return (Double)this.value >= (Double)pathResult;
            }
        }
        return false;
    }

    private boolean compSmalerOrEqual(Object pathResult) {
        if (this.value instanceof Integer){
            if (pathResult instanceof Integer) {
                return (Integer)this.value <= (Integer)pathResult;
            }else if(pathResult instanceof Double){
                return (Integer)this.value <= (Double)pathResult;
            }
        }else if (this.value instanceof Double){
            if (pathResult instanceof Integer) {
                return (Double)this.value <= (Integer)pathResult;
            }else if(pathResult instanceof Double){
                return (Double)this.value <= (Double)pathResult;
            }
        }
        return false;
    }

    private boolean compBigger(Object pathResult) {
        if (this.value instanceof Integer){
            if (pathResult instanceof Integer) {
                return (Integer)this.value > (Integer)pathResult;
            }else if(pathResult instanceof Double){
                return (Integer)this.value > (Double)pathResult;
            }
        }else if (this.value instanceof Double){
            if (pathResult instanceof Integer) {
                return (Double)this.value > (Integer)pathResult;
            }else if(pathResult instanceof Double){
                return (Double)this.value > (Double)pathResult;
            }
        }
        return false;
    }

    private boolean compSmaler(Object pathResult) {
        if (this.value instanceof Integer){
            if (pathResult instanceof Integer) {
                return (Integer)this.value < (Integer)pathResult;
            }else if(pathResult instanceof Double){
                return (Integer)this.value < (Double)pathResult;
            }
        }else if (this.value instanceof Double){
            if (pathResult instanceof Integer) {
                return (Double)this.value < (Integer)pathResult;
            }else if(pathResult instanceof Double){
                return (Double)this.value < (Double)pathResult;
            }
        }
        return false;
    }

    private boolean compRegexMatch(Object pathResult) {
        if(pathResult instanceof String && this.value instanceof String){
            return ((String)pathResult).matches((String)this.value);
        }
        return false;
    }

}
