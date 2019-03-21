import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class RuleSetTest {

    private String message = new JSONObject()
            .put("str", "foo")
            .put("number", 42)
            .put("decimal", 42.1)
            .put("arr", new JSONArray()
                    .put(new JSONObject()
                            .put("name", "a")
                    )
                    .put(new JSONObject()
                            .put("name", "b")
                    )
            )
            .toString();

    private RuleSet createRuleSet(String scope, String path, String comp, Object value){
        return new RuleSet(new JSONObject()
                .put("scope", "any")
                .put("rules", new JSONArray()
                        .put(new JSONObject()
                                .put("scope", scope)
                                .put("path", path)
                                .put("comparison", comp)
                                .put("value", value)
                        )
                )
                .toString());
    }

    @Test
    public void testCheck(){
        Assert.assertTrue(createRuleSet("any","$.str", "==", "foo").check(message));
        Assert.assertFalse(createRuleSet("any","$.str", "==", "bar").check(message));
        Assert.assertFalse(createRuleSet("any","$.stra", "==", "foo").check(message));
        Assert.assertFalse(createRuleSet("any","$.number", "==", "foo").check(message));

        Assert.assertTrue(createRuleSet("any","$.number", "==", 42).check(message));
        Assert.assertTrue(createRuleSet("any","$.number", "==", 42.0).check(message));
        Assert.assertFalse(createRuleSet("any","$.number", "==", 13).check(message));
        Assert.assertFalse(createRuleSet("any","$.numbera", "==", 42).check(message));
        Assert.assertFalse(createRuleSet("any","$.number", "==", 42.1).check(message));
        Assert.assertFalse(createRuleSet("any","$.number", "==", "42").check(message));

        Assert.assertTrue(createRuleSet("any","$.arr[*].name", "==", "a").check(message));
        Assert.assertFalse(createRuleSet("all","$.arr[*].name", "==", "a").check(message));
        Assert.assertFalse(createRuleSet("all","$.arr[*].name", "!=", "a").check(message));
        Assert.assertTrue(createRuleSet("all","$.arr[*].name", "!=", "c").check(message));

        //============================

        Assert.assertTrue(createRuleSet("all","$.arr[*].name", "regex", "[ab]").check(message));
        Assert.assertFalse(createRuleSet("max1","$.arr[*].name", "regex", "[ab]").check(message));
        Assert.assertTrue(createRuleSet("min1","$.arr[*].name", "regex", "[ab]").check(message));
        Assert.assertTrue(createRuleSet("2","$.arr[*].name", "regex", "[ab]").check(message));

        //============================

        Assert.assertTrue(createRuleSet("any","$.number", ">=", 43).check(message));
        Assert.assertTrue(createRuleSet("any","$.number", ">=", 42).check(message));
        Assert.assertFalse(createRuleSet("any","$.number", ">=", 24).check(message));

        Assert.assertTrue(createRuleSet("any","$.number", ">=", 43.0).check(message));
        Assert.assertTrue(createRuleSet("any","$.number", ">=", 42.0).check(message));
        Assert.assertFalse(createRuleSet("any","$.number", ">=", 24.0).check(message));

        Assert.assertTrue(createRuleSet("any","$.number", ">=", 43.1).check(message));
        Assert.assertFalse(createRuleSet("any","$.number", ">=", 24.3).check(message));

        Assert.assertTrue(createRuleSet("any","$.decimal", ">=", 43.1).check(message));
        Assert.assertTrue(createRuleSet("any","$.decimal", ">=", 42.1).check(message));
        Assert.assertFalse(createRuleSet("any","$.decimal", ">=", 24.1).check(message));


        Assert.assertTrue(createRuleSet("any","$.decimal", ">=", 43).check(message));
        Assert.assertFalse(createRuleSet("any","$.decimal", ">=", 42).check(message));
        Assert.assertFalse(createRuleSet("any","$.decimal", ">=", 24).check(message));

        Assert.assertTrue(createRuleSet("any","$.decimal", ">=", 43).check(message));
        Assert.assertFalse(createRuleSet("any","$.decimal", ">=", 24).check(message));
        
        //============================

        Assert.assertFalse(createRuleSet("any","$.number", "<=", 43).check(message));
        Assert.assertTrue(createRuleSet("any","$.number", "<=", 42).check(message));
        Assert.assertTrue(createRuleSet("any","$.number", "<=", 24).check(message));

        Assert.assertFalse(createRuleSet("any","$.number", "<=", 43.0).check(message));
        Assert.assertTrue(createRuleSet("any","$.number", "<=", 42.0).check(message));
        Assert.assertTrue(createRuleSet("any","$.number", "<=", 24.0).check(message));

        Assert.assertFalse(createRuleSet("any","$.number", "<=", 43.1).check(message));
        Assert.assertTrue(createRuleSet("any","$.number", "<=", 24.3).check(message));

        Assert.assertFalse(createRuleSet("any","$.decimal", "<=", 43.1).check(message));
        Assert.assertTrue(createRuleSet("any","$.decimal", "<=", 42.1).check(message));
        Assert.assertTrue(createRuleSet("any","$.decimal", "<=", 24.1).check(message));


        Assert.assertFalse(createRuleSet("any","$.decimal", "<=", 43).check(message));
        Assert.assertTrue(createRuleSet("any","$.decimal", "<=", 42).check(message));
        Assert.assertTrue(createRuleSet("any","$.decimal", "<=", 24).check(message));

        Assert.assertFalse(createRuleSet("any","$.decimal", "<=", 43).check(message));
        Assert.assertTrue(createRuleSet("any","$.decimal", "<=", 24).check(message));

        //============================

        Assert.assertFalse(createRuleSet("any","$.number", "<", 43).check(message));
        Assert.assertFalse(createRuleSet("any","$.number", "<", 42).check(message));
        Assert.assertTrue(createRuleSet("any","$.number", "<", 24).check(message));

        Assert.assertFalse(createRuleSet("any","$.number", "<", 43.0).check(message));
        Assert.assertFalse(createRuleSet("any","$.number", "<", 42.0).check(message));
        Assert.assertTrue(createRuleSet("any","$.number", "<", 24.0).check(message));

        Assert.assertFalse(createRuleSet("any","$.number", "<", 43.1).check(message));
        Assert.assertTrue(createRuleSet("any","$.number", "<", 24.3).check(message));

        Assert.assertFalse(createRuleSet("any","$.decimal", "<", 43.1).check(message));
        Assert.assertFalse(createRuleSet("any","$.decimal", "<", 42.1).check(message));
        Assert.assertTrue(createRuleSet("any","$.decimal", "<", 24.1).check(message));


        Assert.assertFalse(createRuleSet("any","$.decimal", "<", 43).check(message));
        Assert.assertTrue(createRuleSet("any","$.decimal", "<", 42).check(message));
        Assert.assertTrue(createRuleSet("any","$.decimal", "<", 24).check(message));

        Assert.assertFalse(createRuleSet("any","$.decimal", "<", 43).check(message));
        Assert.assertTrue(createRuleSet("any","$.decimal", "<", 24).check(message));

        //============================

        Assert.assertTrue(createRuleSet("any","$.number", ">", 43).check(message));
        Assert.assertFalse(createRuleSet("any","$.number", ">", 42).check(message));
        Assert.assertFalse(createRuleSet("any","$.number", ">", 24).check(message));

        Assert.assertTrue(createRuleSet("any","$.number", ">", 43.0).check(message));
        Assert.assertFalse(createRuleSet("any","$.number", ">", 42.0).check(message));
        Assert.assertFalse(createRuleSet("any","$.number", ">", 24.0).check(message));

        Assert.assertTrue(createRuleSet("any","$.number", ">", 43.1).check(message));
        Assert.assertFalse(createRuleSet("any","$.number", ">", 24.3).check(message));

        Assert.assertTrue(createRuleSet("any","$.decimal", ">", 43.1).check(message));
        Assert.assertFalse(createRuleSet("any","$.decimal", ">", 42.1).check(message));
        Assert.assertFalse(createRuleSet("any","$.decimal", ">", 24.1).check(message));


        Assert.assertTrue(createRuleSet("any","$.decimal", ">", 43).check(message));
        Assert.assertFalse(createRuleSet("any","$.decimal", ">", 42).check(message));
        Assert.assertFalse(createRuleSet("any","$.decimal", ">", 24).check(message));

        Assert.assertTrue(createRuleSet("any","$.decimal", ">", 43).check(message));
        Assert.assertFalse(createRuleSet("any","$.decimal", ">", 24).check(message));

    }
}
