/*
 * Copyright (C) 2016 David Dejori, Alex Lardschneider
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.sasabz.android.sasabus.beacon.ecopoints.badge;

import it.sasabz.android.sasabus.R;
import it.sasabz.android.sasabus.beacon.Beacon;
import it.sasabz.android.sasabus.beacon.bus.BusBeacon;

public class FirstStepBadge extends InAppBadge {

    public FirstStepBadge() {
        super(120, R.string.badge_the_first_step_title, R.string.badge_the_first_step_subtitle,
                R.drawable.badge_blue_bus, 20);
    }

    @Override
    public boolean evaluate(Beacon beacon) {
        if (!super.evaluate(beacon)) {
            return false;
        }

        // Since we only want to know if the user scanned a bus beacon, we only have to
        // check if the passed beacon is a bus beacon, as the condition will be met then.
        return beacon instanceof BusBeacon;
    }
}
