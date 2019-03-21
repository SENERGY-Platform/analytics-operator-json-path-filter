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

import org.infai.seits.sepl.operators.Config;
import org.infai.seits.sepl.operators.Message;
import org.infai.seits.sepl.operators.OperatorInterface;

public class Filter implements OperatorInterface {
    private RuleSet ruleset;

    public Filter() {
        this.ruleset = new RuleSet(new Config().getConfigValue("RuleSet", ""));
    }

    @Override
    public void run(Message message) {
        if (this.ruleset.check(message.getMessageString())) {
            message.output("filtered", true);   //signal library to send message to next receiver
        }
    }

    @Override
    public void config(Message ruleSetMessage) {

    }
}
