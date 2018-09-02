/*
 * Copyright (c) 2007 Scott Lembcke, (c) 2011 Jürgen Obernolte
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.physics.jipmunk;

/**
 * @author jobernolte
 */
public class ShapeFilter {

	public static final ShapeFilter ALL = new ShapeFilter(Group.NO_GROUP, Bitmask.ALL, Bitmask.ALL);
	public static final ShapeFilter NONE = new ShapeFilter(Group.NO_GROUP, Bitmask.NONE, Bitmask.NONE);

	private final Group group;
	private final Bitmask categories;
	private final Bitmask mask;

	public ShapeFilter(Group group, Bitmask categories, Bitmask mask) {
		this.group = group;
		this.categories = categories;
		this.mask = mask;
	}

	public Group getGroup() {
		return group;
	}

	public Bitmask getCategories() {
		return categories;
	}

	public Bitmask getMask() {
		return mask;
	}

	public boolean reject(ShapeFilter b) {
		return reject(this, b);
	}

	public static boolean reject(ShapeFilter a, ShapeFilter b) {
		// Reject the collision if:
		return (
				// They are in the same non-zero group.
				(a.group != Group.NO_GROUP && a.group.equals(b.group) ||
						// One of the category/mask combinations fails.
						!a.categories.and(b.mask) || !b.categories.and(a.mask)));
	}
}
