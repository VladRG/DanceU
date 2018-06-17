:-use_module(library(sockets)).
:-use_module( library(lists)).

start_process:-
    format('Initializing Prolog Database\n',[]),	
    flush_output,
    prolog_flag(argv, [PortSocket|_]), 
    atom_chars(PortSocket,LCifre),
    number_chars(Port,LCifre),
    socket_client_open(localhost: Port, Stream, [type(text)]),
    process_text(Stream,0).
				
				
process_text(Stream, C):-
    read(Stream, ReadCommand),
    process_command(Stream, ReadCommand, C).

process_command(Stream, get_dance(Dance_Type), C):-
    dance(Dance_Type, Description, Name),
    format(Stream, '~s#~s\n', [Description, Name]),
    flush_output(Stream),
    C1 is C + 1,
    process_text(Stream, C1).

process_command(Stream, 'list_dances', C):-
    findall(Title, dance(Dance, Description, Title), Dances),
    print('test'),
    format(Stream, '~p#~p#~p#~p#~p#~p#~p#~p#~p#~p#~p#~p#~p#~p#~p#~p#~p#', Dances),
    print('test2'),
    flush_output(Stream).

dance(salsa, 'descriere Salsa', 'Salsa').
dance(tango, 'descriere Tango', 'Tango').
dance(street_dance, 'descriere Street Dance', 'Street Dance').
dance(swing, 'descriere Swing', 'Swing').
dance(classic_balet, 'descriere Balet Clasic', 'Balet Clasic').
dance(paso_doble, 'descriere Paso Doble', 'Paso Doble').
dance(hip_hop, 'descriere Hip Hop', 'Hip Hop').
dance(twist, 'descriere Hip Hop', 'Twist').
dance(modern_dance, 'descriere Hip Hop', 'Dans Modern').
dance(contemporary_dance, 'descriere Hip Hop', 'Dans Contemporan').
dance(zumba, 'descriere Hip Hop', 'Zumba').
dance(classic_waltz, 'descriere Hip Hop', 'Vals Clasic').
dance(breakdance, 'descriere Hip Hop', 'Breakdance').
dance(bachata, 'descriere Hip Hop', 'Bachata').
dance(aerobic, 'descriere Hip Hop', 'Aerobic').
dance(sportive_dance, 'descriere Hip Hop', 'Dans Sportiv').
dance(viennese_waltz, 'descriere Hip Hop', 'Vals Vienez').
    