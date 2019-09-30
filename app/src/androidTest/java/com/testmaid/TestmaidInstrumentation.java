package com.testmaid;

import android.app.Instrumentation;
import android.app.UiAutomation;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.test.uiautomator.UiDevice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
public class TestmaidInstrumentation {

    private UiDevice uiDevice;
    private Instrumentation instrumentation;
    private Bundle arguments = InstrumentationRegistry.getArguments();
    private UiAutomation uiAutomation;
    private Rect screenRect;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
    private StringBuilder allNodesString = new StringBuilder();
    private int foundCount = 0;
    private boolean continueRecurse = true;
    private AccessibilityNodeInfo desiredNode;

    @Before
    public void before() {
        instrumentation = getInstrumentation();
        uiDevice = UiDevice.getInstance(instrumentation);
        screenRect = new Rect(0, 0, uiDevice.getDisplayWidth(), uiDevice.getDisplayHeight());
        logInstrumentation("Testmaid starts her job");
        uiAutomation = instrumentation.getUiAutomation();
        //uiDevice.waitForIdle();
    }

    @Test
    public void runTestmaid() {
        int index = 0;
        int loops = 5;
        if (arguments != null) {
            logInstrumentation("Arguments found");
            Set<String> keys = arguments.keySet();
            if (keys.isEmpty()) {
                printAllNodes();
                logResult(0, "All Nodes Printed");
            } else {
                for (String key : keys) {
                    logInstrumentation("key: " + key + ", value: " + arguments.get(key));
                }
                if (arguments.containsKey("index"))
                    index = Integer.parseInt((String) Objects.requireNonNull(arguments.get("index")));
                if (arguments.containsKey("loops"))
                    loops = Integer.parseInt((String) Objects.requireNonNull(arguments.get("loops")));
                if (index < 1000 && index >= 0 && loops > 0) {
                    String[] actions = {"click", "longclick", "find"};
                    logInstrumentation("Available commands " + Arrays.toString(actions));
                    for (String action : actions) {
                        if (arguments.containsKey(action)) {
                            logInstrumentation("Matching " + action);
                            generic(action, (String) arguments.get(action), index, loops);
                        }
                    }
                } else {
                    logResult(3, "ERROR: index or loops out of bounds - index: " + index + " should be 0-999, loops: " + loops + " should be greater than 0");
                }
            }
        } else {
            logResult(0, "No arguments");
        }
        logInstrumentation("Testmaid has finished her job");
        uiAutomation = null;
        instrumentation = null;
        uiDevice = null;
    }

    public void printAllNodes() {
        allNodesString.setLength(0);
        allNodesString.append("\n---- all nodes");
        findNodeRecursively(null,0,uiAutomation.getRootInActiveWindow(),"");
        allNodesString.append("\nall nodes ----");
        logInstrumentation(allNodesString.toString());
    }

    public void findNodeRecursively(Pattern regex, int index, AccessibilityNodeInfo parentNode, String level) {
        Rect nodeRect = new Rect();
        parentNode.getBoundsInScreen(nodeRect);
        if (nodeRect.intersect(screenRect)) {
            if (regex!=null){ //NOT print all action
                Matcher matcher = regex.matcher(parentNode.toString());
                if (matcher.find()) {
                    foundCount++;
                    if (index == foundCount - 1) {
                        desiredNode = parentNode;
                        continueRecurse = false;
                    }
                }
            } else { //print all action
                allNodesString.append("\n\n").append(level).append(parentNode.toString());
            }
            for (int i = 0; i < parentNode.getChildCount(); i++) {
                if (continueRecurse){
                    findNodeRecursively(regex, index, parentNode.getChild(i), level + " ");
                }
            }
        }
    }

    public void generic(String action, String argument, int index, int loops) {
        logInstrumentation("Trying to " + action + " " + argument + " index: " + index + " loops: " + loops);
        Pattern regex = Pattern.compile(argument);
        while (loops>0) {
            findNodeRecursively(regex, index, uiAutomation.getRootInActiveWindow(), "");
            if (desiredNode!=null) {
                break;
            }
            foundCount = 0;
            logInstrumentation( "Looping");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            loops--;
        }
        if (desiredNode==null) {
            logResult(1, "ERROR: Can't find: " + action + " " + argument + "[" + index + "]");
        } else {
            performAction(desiredNode,action);
        }
    }

    public void performAction(AccessibilityNodeInfo node, String action) {
        Rect nodeRect = new Rect();
        node.getBoundsInScreen(nodeRect);
        switch (action) {
            case "click":
                logResult(0, "Clicking on: " + node.toString());
                uiDevice.click(nodeRect.centerX(), nodeRect.centerY());
                break;
            case "longclick":
                logResult(0, "Long clicking on: " + node.toString());
                uiDevice.swipe(nodeRect.centerX(), nodeRect.centerY(), nodeRect.centerX(), nodeRect.centerY(), 200);
                break;
            case "find":
                logResult(0, "Node found: " + node.toString());
                break;
            default:
                logResult(2, "ERROR: Unknown action:" + action);
                break;
        }
    }

    public void logInstrumentation(String logString) {
        Bundle bundle = new Bundle();
        bundle.putString("-Testmaid-", dateFormat.format(new Date()) + " " + logString);
        instrumentation.sendStatus(0, bundle);
    }

    public void logResult(int exitCode, String logString) {
        logInstrumentation("Testmaid >>>" + logString + "<<< Testmaid");
        logInstrumentation("Testmaid exit code:" + exitCode);
    }
}