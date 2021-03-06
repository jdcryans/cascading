/*
 * Copyright (c) 2007-2009 Concurrent, Inc. All Rights Reserved.
 *
 * Project and contact information: http://www.cascading.org/
 *
 * This file is part of the Cascading project.
 *
 * Cascading is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Cascading is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Cascading.  If not, see <http://www.gnu.org/licenses/>.
 */

package cascading.tuple;

/** Interface TupleEntryCollector is used to allow {@link cascading.operation.BaseOperation} instances to emit result {@link Tuple} values. */
public abstract class TupleEntryCollector
  {
  /** Field declared */
  protected Fields declared;

  protected TupleEntryCollector()
    {
    }

  /**
   * Constructor TupleCollector creates a new TupleCollector instance.
   *
   * @param declared of type Fields
   */
  public TupleEntryCollector( Fields declared )
    {
    if( declared == null )
      throw new IllegalArgumentException( "declared fields must not be null" );

    this.declared = declared;
    }

  /**
   * Method add inserts the given {@link TupleEntry} into the outgoing stream. Note the method {@link #add(Tuple)} is
   * more efficient as it simply calls {@link TupleEntry#getTuple()};
   *
   * @param entry of type TupleEntry
   */
  public void add( TupleEntry entry )
    {
    add( entry.getTuple() );
    }

  /**
   * Method add inserts the given {@link Tuple} into the outgoing stream.
   *
   * @param tuple of type Tuple
   */
  public void add( Tuple tuple )
    {
    if( tuple.isEmpty() )
      return;

    if( declared != null && !declared.isUnknown() && declared.size() != tuple.size() )
      throw new TupleException( "operation added the wrong number of fields, expected: " + declared.print() + ", got result size: " + tuple.size() );

    collect( tuple );
    }

  protected abstract void collect( Tuple tuple );

  /**
   * Method close closes the underlying resource being written to. This method should be called when no more {@link Tuple}
   * instances will be written out.
   */
  public void close()
    {
    // do nothing
    }

  }