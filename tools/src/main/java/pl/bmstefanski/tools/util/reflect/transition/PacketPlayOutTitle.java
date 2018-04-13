/*
 MIT License

 Copyright (c) 2018 Whippy Tools

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package pl.bmstefanski.tools.util.reflect.transition;

import pl.bmstefanski.tools.util.reflect.AbstractPacket;
import pl.bmstefanski.tools.util.reflect.Reflections;

import java.lang.reflect.InvocationTargetException;

public class PacketPlayOutTitle extends AbstractPacket {

    private static Class<?> packetClass;
    private static Class<?> titleActionClass;
    private static Class<?> chatSerializerClass;
    private static Class<?> chatBaseComponentClass;

    static {
        packetClass = Reflections.getCraftClass("PacketPlayOutTitle");
        titleActionClass = Reflections.getCraftClass("PacketPlayOutTitle$EnumTitleAction");
        chatSerializerClass = Reflections.getCraftClass("IChatBaseComponent$ChatSerializer");
        chatBaseComponentClass = Reflections.getCraftClass("IChatBaseComponent");
    }

    public enum EnumTitleAction {
        TITLE(0),
        SUBTITLE(1),
        ACTIONBAR(2),
        TIMES(3),
        CLEAR(4),
        RESET(5);


        private final Object[] actions = titleActionClass.getEnumConstants();
        private final Object action;

        EnumTitleAction(int id) {
            action = actions[id];
        }

        public Object getCraftAction() {
            return action;
        }
    }

    public PacketPlayOutTitle(EnumTitleAction enumTitleAction, String json) {
        this(enumTitleAction, json, -1, -1, -1);
    }

    public PacketPlayOutTitle(EnumTitleAction enumTitleAction, String json, int fadeIn, int time, int fadeOut) {
        Object serialized = null;

        try {
            if (json != null) {
                serialized = Reflections.getMethod(chatSerializerClass, "a", String.class).invoke(null, json);
            }

            packet = packetClass.getConstructor(titleActionClass, chatBaseComponentClass, Integer.TYPE, Integer.TYPE, Integer.TYPE).newInstance(enumTitleAction.getCraftAction(), serialized, fadeIn, time, fadeOut);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
