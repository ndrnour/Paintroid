/*
 *   This file is part of Paintroid, a software part of the Catroid project.
 *   Copyright (C) 2010  Catroid development team
 *   <http://code.google.com/p/catroid/wiki/Credits>
 *
 *   Paintroid is free software: you can redistribute it and/or modify it
 *   under the terms of the GNU Affero General Public License as published
 *   by the Free Software Foundation, either version 3 of the License, or
 *   at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package at.tugraz.ist.paintroid.commandmanagement.implementation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import at.tugraz.ist.paintroid.PaintroidApplication;

public class PathCommand extends BaseCommand {

	protected Path path;

	public PathCommand(Paint paint, Path path) {
		super(paint);
		this.path = new Path(path);
	}

	@Override
	public void run(Canvas canvas) {
		Log.d(PaintroidApplication.TAG, "PathCommand.run");
		canvas.drawPath(path, paint);
	}

	@Override
	public boolean isUndoable() {
		return true;
	}

}