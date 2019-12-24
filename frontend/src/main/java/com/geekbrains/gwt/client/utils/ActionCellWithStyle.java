package com.geekbrains.gwt.client.utils;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;


public class ActionCellWithStyle<C> extends AbstractCell<C> {
    private final SafeHtml html;
    private final com.google.gwt.cell.client.ActionCell.Delegate<C> delegate;

    public ActionCellWithStyle(SafeHtml message, String style, com.google.gwt.cell.client.ActionCell.Delegate<C> delegate) {
        super(new String[]{"click", "keydown"});
        this.delegate = delegate;
        this.html = (new SafeHtmlBuilder()).appendHtmlConstant("<button type=\"button\" tabindex=\"-1\" class=\"" + style + "\">").append(message).appendHtmlConstant("</button>").toSafeHtml();
    }

    public ActionCellWithStyle(String text, String style, com.google.gwt.cell.client.ActionCell.Delegate<C> delegate) {
        this(SafeHtmlUtils.fromString(text), style, delegate);
    }

    public void onBrowserEvent(Context context, Element parent, C value, NativeEvent event, ValueUpdater<C> valueUpdater) {
        super.onBrowserEvent(context, parent, value, event, valueUpdater);
        if ("click".equals(event.getType())) {
            EventTarget eventTarget = event.getEventTarget();
            if (!Element.is(eventTarget)) {
                return;
            }

            if (parent.getFirstChildElement().isOrHasChild(Element.as(eventTarget))) {
                this.onEnterKeyDown(context, parent, value, event, valueUpdater);
            }
        }

    }

    public void render(Context context, C value, SafeHtmlBuilder sb) {
        sb.append(this.html);
    }

    protected void onEnterKeyDown(Context context, Element parent, C value, NativeEvent event, ValueUpdater<C> valueUpdater) {
        this.delegate.execute(value);
    }

    public interface Delegate<T> {
        void execute(T var1);
    }
}
